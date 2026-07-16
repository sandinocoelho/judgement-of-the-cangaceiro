# CANGA-20 — redesenha as regiões de título do atlas banners.png com o nome
# do fork, em pixel art: lettering renderizado com a própria pixel_font.ttf do
# jogo (sem anti-aliasing), upscale inteiro, gradiente dourado->couro e
# contorno escuro aplicados pixel a pixel. Regiões do atlas inalteradas
# (BannerSprites não muda); GameOver/BossSlain intocados.
# Uso: powershell -File generate-banners.ps1  (na pasta art/banner)
$ErrorActionPreference = 'Stop'
Add-Type -AssemblyName System.Drawing
Set-Location $PSScriptRoot
$repo = Resolve-Path (Join-Path $PSScriptRoot '..\..')
$bannersPath = Join-Path $repo 'core\src\main\assets\interfaces\banners.png'
$fontPath = Join-Path $repo 'core\src\main\assets\fonts\pixel_font.ttf'

# paleta (mesma família do ícone C1)
$cOutline = [System.Drawing.Color]::FromArgb(255, 0x2B, 0x1A, 0x10)
$cTop     = [System.Drawing.Color]::FromArgb(255, 0xF4, 0xC9, 0x5D)  # dourado
$cBottom  = [System.Drawing.Color]::FromArgb(255, 0x9C, 0x6B, 0x2F)  # couro
$cGlow    = [System.Drawing.Color]::FromArgb(255, 0xFF, 0xE9, 0xB0)  # glow quente

$fonts = New-Object System.Drawing.Text.PrivateFontCollection
$fonts.AddFontFile($fontPath)
$family = $fonts.Families[0]

# renderiza texto 1-bit (sem AA) e devolve bitmap recortado ao conteúdo
function Render-PixelText([string]$text, [float]$emSize) {
    $font = New-Object System.Drawing.Font($family, $emSize, [System.Drawing.FontStyle]::Regular, [System.Drawing.GraphicsUnit]::Pixel)
    $tmp = New-Object System.Drawing.Bitmap 1024, 256
    $g = [System.Drawing.Graphics]::FromImage($tmp)
    $g.TextRenderingHint = [System.Drawing.Text.TextRenderingHint]::SingleBitPerPixelGridFit
    $g.DrawString($text, $font, [System.Drawing.Brushes]::White, 0, 0)
    $g.Dispose(); $font.Dispose()
    # bounding box do conteúdo
    $minX = 1024; $minY = 256; $maxX = -1; $maxY = -1
    for ($y = 0; $y -lt 256; $y++) {
        for ($x = 0; $x -lt 1024; $x++) {
            if ($tmp.GetPixel($x, $y).A -gt 128) {
                if ($x -lt $minX) { $minX = $x }; if ($x -gt $maxX) { $maxX = $x }
                if ($y -lt $minY) { $minY = $y }; if ($y -gt $maxY) { $maxY = $y }
            }
        }
    }
    if ($maxX -lt 0) { throw "texto vazio: $text" }
    $wd = $maxX - $minX + 1; $ht = $maxY - $minY + 1
    $crop = New-Object System.Drawing.Bitmap $wd, $ht
    for ($y = 0; $y -lt $ht; $y++) {
        for ($x = 0; $x -lt $wd; $x++) {
            if ($tmp.GetPixel($minX + $x, $minY + $y).A -gt 128) {
                $crop.SetPixel($x, $y, [System.Drawing.Color]::White)
            }
        }
    }
    $tmp.Dispose()
    return , $crop
}

# upscale inteiro (nearest-neighbor)
function Scale-Int([System.Drawing.Bitmap]$src, [int]$factor) {
    if ($factor -le 1) { return , $src }
    $dst = New-Object System.Drawing.Bitmap ($src.Width * $factor), ($src.Height * $factor)
    for ($y = 0; $y -lt $src.Height; $y++) {
        for ($x = 0; $x -lt $src.Width; $x++) {
            if ($src.GetPixel($x, $y).A -gt 128) {
                for ($dy = 0; $dy -lt $factor; $dy++) {
                    for ($dx = 0; $dx -lt $factor; $dx++) {
                        $dst.SetPixel($x * $factor + $dx, $y * $factor + $dy, [System.Drawing.Color]::White)
                    }
                }
            }
        }
    }
    return , $dst
}

# aplica gradiente vertical de 2 tons + contorno 1px + sombra 1px
function Stylize([System.Drawing.Bitmap]$mask, [bool]$glow) {
    $w = $mask.Width + 4; $h = $mask.Height + 4
    $out = New-Object System.Drawing.Bitmap $w, $h
    $solid = New-Object 'bool[,]' $w, $h
    for ($y = 0; $y -lt $mask.Height; $y++) {
        for ($x = 0; $x -lt $mask.Width; $x++) {
            if ($mask.GetPixel($x, $y).A -gt 128) { $solid[($x+2), ($y+2)] = $true }
        }
    }
    for ($y = 0; $y -lt $h; $y++) {
        $t = [double]($y - 2) / [Math]::Max(1, $mask.Height - 1)
        if ($t -lt 0) { $t = 0 }; if ($t -gt 1) { $t = 1 }
        $r = [int]($cTop.R + ($cBottom.R - $cTop.R) * $t)
        $gg = [int]($cTop.G + ($cBottom.G - $cTop.G) * $t)
        $b = [int]($cTop.B + ($cBottom.B - $cTop.B) * $t)
        $fill = if ($glow) { $cGlow } else { [System.Drawing.Color]::FromArgb(255, $r, $gg, $b) }
        for ($x = 0; $x -lt $w; $x++) {
            if ($solid[$x, $y]) { $out.SetPixel($x, $y, $fill) }
        }
    }
    if (-not $glow) {
        # contorno + sombra
        for ($y = 0; $y -lt $h; $y++) {
            for ($x = 0; $x -lt $w; $x++) {
                if (-not $solid[$x, $y]) {
                    $edge = $false
                    foreach ($d in @(@(-1,0), @(1,0), @(0,-1), @(0,1))) {
                        $nx = $x + $d[0]; $ny = $y + $d[1]
                        if ($nx -ge 0 -and $ny -ge 0 -and $nx -lt $w -and $ny -lt $h -and $solid[$nx, $ny]) { $edge = $true; break }
                    }
                    $shadow = ($y -ge 2 -and $x -ge 1 -and $solid[($x-1), ($y-2)])
                    if ($edge) { $out.SetPixel($x, $y, $cOutline) }
                    elseif ($shadow -and $out.GetPixel($x, $y).A -eq 0) {
                        $out.SetPixel($x, $y, [System.Drawing.Color]::FromArgb(120, 0x2B, 0x1A, 0x10))
                    }
                }
            }
        }
    }
    return , $out
}

function Compose-Title([int]$regW, [int]$regH, [object[]]$lines, [bool]$glow) {
    # renderiza cada linha na fonte em tamanho nativo (em 9) e escolhe a maior
    # escala inteira em que TODAS as linhas principais cabem na largura e o
    # conjunto cabe na altura — pixels grandes, como manda o padrão pixel art
    # em 12 é o tamanho em que a pixel_font renderiza sem artefatos de glifo
    # via GDI+ (testado: 8/9/18 degradam D e I; 12/14/16 são limpos)
    $masks = @()
    foreach ($ln in $lines) { $masks += , (Render-PixelText $ln.text 12) }

    $mainScale = 100
    for ($i = 0; $i -lt $lines.Count; $i++) {
        if ($lines[$i].rel -ge 1.0) {
            $fit = [Math]::Floor(($regW - 6) / $masks[$i].Width)
            if ($fit -lt $mainScale) { $mainScale = [int]$fit }
        }
    }
    if ($mainScale -lt 1) { $mainScale = 1 }

    while ($mainScale -ge 1) {
        $totalH = 0
        $scales = @()
        for ($i = 0; $i -lt $lines.Count; $i++) {
            $s = [Math]::Max(1, [int][Math]::Round($mainScale * $lines[$i].rel))
            $scales += $s
            $totalH += $masks[$i].Height * $s + 4
        }
        if ($totalH + ($lines.Count - 1) * 2 -le $regH) { break }
        $mainScale--
    }
    if ($mainScale -lt 1) { $mainScale = 1 }

    $canvas = New-Object System.Drawing.Bitmap $regW, $regH
    $g = [System.Drawing.Graphics]::FromImage($canvas)
    $rendered = @()
    $totalH = 0
    for ($i = 0; $i -lt $lines.Count; $i++) {
        $sty = Stylize (Scale-Int $masks[$i] $scales[$i]) $glow
        $rendered += , $sty
        $totalH += $sty.Height
    }
    $gap = [Math]::Max(1, [int](($regH - $totalH) / ($lines.Count + 1)))
    $y = [int](($regH - $totalH - $gap * ($lines.Count - 1)) / 2)
    foreach ($sty in $rendered) {
        $x = [int](($regW - $sty.Width) / 2)
        $g.DrawImageUnscaled($sty, $x, $y)
        $y += $sty.Height + $gap
    }
    $g.Dispose()
    return , $canvas
}

# --- monta o atlas ---
$bytes = [System.IO.File]::ReadAllBytes($bannersPath)
$ms = New-Object System.IO.MemoryStream(, $bytes)
$atlas = New-Object System.Drawing.Bitmap $ms

$g = [System.Drawing.Graphics]::FromImage($atlas)
$g.CompositingMode = [System.Drawing.Drawing2D.CompositingMode]::SourceCopy
$clear = New-Object System.Drawing.SolidBrush ([System.Drawing.Color]::FromArgb(0, 0, 0, 0))
# limpa as 4 regiões de título (port, glow-port, land, glow-land)
$g.FillRectangle($clear, 0, 0, 139, 100)
$g.FillRectangle($clear, 139, 0, 139, 100)
$g.FillRectangle($clear, 0, 100, 240, 57)
$g.FillRectangle($clear, 240, 100, 240, 57)

# portrait 139x100: JUDGEMENT / of the / CANGACEIRO
$portLines = @(
    @{ text = 'JUDGEMENT'; rel = 1.0 },
    @{ text = 'of the'; rel = 0.5 },
    @{ text = 'CANGACEIRO'; rel = 1.0 }
)
# landscape 240x57: JUDGEMENT of the / CANGACEIRO (hierarquia de 2 linhas)
$landLines = @(
    @{ text = 'JUDGEMENT of the'; rel = 1.0 },
    @{ text = 'CANGACEIRO'; rel = 1.0 }
)

$port     = Compose-Title 139 100 $portLines $false
$portGlow = Compose-Title 139 100 $portLines $true
$land     = Compose-Title 240 57 $landLines $false
$landGlow = Compose-Title 240 57 $landLines $true

$g.DrawImageUnscaled($port, 0, 0)
$g.DrawImageUnscaled($portGlow, 139, 0)
$g.DrawImageUnscaled($land, 0, 100)
$g.DrawImageUnscaled($landGlow, 240, 100)
$g.Dispose()

$atlas.Save((Join-Path $PSScriptRoot 'banners-new.png'), [System.Drawing.Imaging.ImageFormat]::Png)
$atlas.Dispose(); $ms.Dispose()
Write-Output 'banners-new.png gerado (preview antes de substituir o asset)'
