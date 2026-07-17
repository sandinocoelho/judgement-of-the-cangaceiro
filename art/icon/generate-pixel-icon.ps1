# CANGA-32 — ícone em PIXEL ART: sprite desenhado num grid de 32x32 (Conceito A,
# chapéu de meia-lua de Lampião com estrela de seis pontas), ampliado por
# nearest-neighbor em fatores inteiros. Substitui os PNGs 1024 do placeholder
# vetorial em art/icon/; rode depois generate-android-mipmaps.sh e
# generate-desktop-icons.ps1 para refazer os conjuntos por plataforma.
# Uso: powershell -File generate-pixel-icon.ps1  (na pasta art/icon)
$ErrorActionPreference = 'Stop'
Add-Type -AssemblyName System.Drawing
Set-Location $PSScriptRoot

# ---------------- paleta (chaves todas minúsculas e distintas) ----------------
$P = @{
    'k' = [System.Drawing.Color]::FromArgb(255, 0x2B, 0x1A, 0x10)  # contorno
    'd' = [System.Drawing.Color]::FromArgb(255, 0x5D, 0x3A, 0x1A)  # couro escuro (meia-lua)
    'm' = [System.Drawing.Color]::FromArgb(255, 0x7A, 0x4E, 0x22)  # couro médio (copa)
    'l' = [System.Drawing.Color]::FromArgb(255, 0x9C, 0x6B, 0x2F)  # couro claro (aba/friso)
    'h' = [System.Drawing.Color]::FromArgb(255, 0xB8, 0x85, 0x4A)  # highlight
    'g' = [System.Drawing.Color]::FromArgb(255, 0xE8, 0xB4, 0x4A)  # tachas de ouro
    's' = [System.Drawing.Color]::FromArgb(255, 0xF4, 0xC9, 0x5D)  # estrela (ouro claro)
    'b' = [System.Drawing.Color]::FromArgb(255, 0x4E, 0x31, 0x15)  # cinta da copa
}

# ---------------- sprite 32x32 (grid autoral) ----------------
# '.' = transparente; 's' = estrela (vira buraco no monochrome)
$rows = @(
    '................................',  # 0
    '................................',  # 1
    '................................',  # 2
    '............kkkkkkkk............',  # 3
    '..........kllllkkllllk..........',  # 4
    '........klldddksskdddllk........',  # 5
    '.......klgdddksssskdddglk.......',  # 6
    '.....kldddksssssssssskdddlk.....',  # 7  ponta lateral superior (larga)
    '.....kldddddksssssskdddddlk.....',  # 8  cintura do hexagrama
    '....klgdddksssssssssskdddglk....',  # 9  ponta lateral inferior (larga)
    '....kldddddddksssskdddddddlk....',  # 10
    '...kldddddddddksskdddddddddlk...',  # 11 ponta de baixo
    '...klgdddddddddkkdddddddddglk...',  # 12 contorno sob a ponta
    '...klddddddddkkkkkkddddddddlk...',  # 13
    '...klddddddkhhmmmmmmkddddddlk...',  # 14
    '...kldddddkhmmmmmmmmmkdddddlk...',  # 15
    '....kkkkkkhmmmmmmmmmmmkkkkkk....',  # 16
    '........khmmmmmmmmmmmmmk........',  # 17
    '........khmmmmmmmmmmmmmk........',  # 18
    '........kmmmmmmmmmmmmmmk........',  # 19
    '........kbbbbbbbbbbbbbbk........',  # 20
    '........kbbbbbbbbbbbbbbk........',  # 21
    '......kllllllllllllllllllk......',  # 22
    '...kllllllllllllllllllllllllk...',  # 23
    '..kllllllllllllllllllllllllllk..',  # 24
    '..kmmmmmmmmmmmmmmmmmmmmmmmmmmk..',  # 25
    '....kmmmmmmmmmmmmmmmmmmmmmmk....',  # 26
    '......kkkkkkkkkkkkkkkkkkkk......',  # 27
    '................................',  # 28
    '................................',  # 29
    '................................',  # 30
    '................................'   # 31
)
$SIZE = 32
if ($rows.Count -ne $SIZE) { throw "grid tem $($rows.Count) linhas, esperado $SIZE" }
for ($i = 0; $i -lt $SIZE; $i++) {
    if ($rows[$i].Length -ne $SIZE) { throw ("linha {0} tem {1} colunas, esperado {2}" -f $i, $rows[$i].Length, $SIZE) }
    foreach ($ch in $rows[$i].ToCharArray()) {
        if ($ch -ne '.' -and -not $P.ContainsKey([string]$ch)) {
            throw ("linha {0}: caractere desconhecido '{1}' (U+{2:X4})" -f $i, $ch, [int]$ch)
        }
    }
}

function New-Sprite([string[]]$grid) {
    $bmp = New-Object System.Drawing.Bitmap $SIZE, $SIZE
    for ($y = 0; $y -lt $SIZE; $y++) {
        for ($x = 0; $x -lt $SIZE; $x++) {
            $c = $grid[$y][$x]
            if ($c -ne '.') { $bmp.SetPixel($x, $y, $P[[string]$c]) }
        }
    }
    return , $bmp
}

# amplia por fator inteiro e centraliza num canvas 1024 (pixels crisp)
function Place-1024([System.Drawing.Bitmap]$sprite, [int]$factor, [System.Drawing.Bitmap]$bg) {
    $canvas = New-Object System.Drawing.Bitmap 1024, 1024
    $g = [System.Drawing.Graphics]::FromImage($canvas)
    $g.InterpolationMode = 'NearestNeighbor'
    $g.PixelOffsetMode = 'Half'
    if ($bg) { $g.DrawImage($bg, 0, 0, 1024, 1024) }
    $sz = $SIZE * $factor
    $off = [int]((1024 - $sz) / 2)
    $g.DrawImage($sprite, $off, $off, $sz, $sz)
    $g.Dispose()
    return , $canvas
}

$hat = New-Sprite $rows

# ---------------- fundos 32x32 (céu liso + dither 2x2 na metade de baixo) ----------------
function New-DitherBg([System.Drawing.Color]$a, [System.Drawing.Color]$b) {
    $bmp = New-Object System.Drawing.Bitmap $SIZE, $SIZE
    for ($y = 0; $y -lt $SIZE; $y++) {
        for ($x = 0; $x -lt $SIZE; $x++) {
            $useB = ($y -ge 16) -and ((([int]($x / 2)) + ([int]($y / 2))) % 2 -eq 0)
            $bmp.SetPixel($x, $y, $(if ($useB) { $b } else { $a }))
        }
    }
    return , $bmp
}
$bgRelease = New-DitherBg ([System.Drawing.Color]::FromArgb(255, 0xC7, 0x5B, 0x39)) ([System.Drawing.Color]::FromArgb(255, 0xA9, 0x4A, 0x2C))
$bgDebug   = New-DitherBg ([System.Drawing.Color]::FromArgb(255, 0x3A, 0x50, 0x78)) ([System.Drawing.Color]::FromArgb(255, 0x2C, 0x3E, 0x63))

# ---------------- monochrome: silhueta branca, estrela vazada ----------------
$mono = New-Object System.Drawing.Bitmap $SIZE, $SIZE
for ($y = 0; $y -lt $SIZE; $y++) {
    for ($x = 0; $x -lt $SIZE; $x++) {
        $c = $rows[$y][$x]
        if ($c -eq '.') { continue }
        if ([string]$c -ceq 's') { continue }  # -ceq: case-sensitive; estrela = buraco
        $mono.SetPixel($x, $y, [System.Drawing.Color]::White)
    }
}

# ---------------- exporta ----------------
# master: chapéu grande (28x = 896px) sobre fundo full-bleed
(Place-1024 $hat 28 $bgRelease).Save("$PSScriptRoot\icon-master-1024.png")
(Place-1024 $hat 28 $bgDebug).Save("$PSScriptRoot\icon-master-debug-1024.png")
# adaptive foreground/monochrome: 21x = 672px, dentro da zona segura de 66% (~683px)
(Place-1024 $hat 21 $null).Save("$PSScriptRoot\icon-adaptive-foreground.png")
(Place-1024 $mono 21 $null).Save("$PSScriptRoot\icon-adaptive-monochrome.png")
# backgrounds full-bleed
(Place-1024 (New-Object System.Drawing.Bitmap $SIZE, $SIZE) 1 $bgRelease).Save("$PSScriptRoot\icon-adaptive-background.png")
(Place-1024 (New-Object System.Drawing.Bitmap $SIZE, $SIZE) 1 $bgDebug).Save("$PSScriptRoot\icon-adaptive-background-debug.png")

# preview do mono sobre fundo colorido (avaliação humana)
$monoPrev = New-Object System.Drawing.Bitmap 1024, 1024
$g = [System.Drawing.Graphics]::FromImage($monoPrev)
$g.Clear([System.Drawing.Color]::FromArgb(255, 0xB0, 0x30, 0x30))
$g.InterpolationMode = 'NearestNeighbor'; $g.PixelOffsetMode = 'Half'
$g.DrawImage($mono, 176, 176, 672, 672)
$g.Dispose()
$monoPrev.Save("$PSScriptRoot\icon-adaptive-monochrome-preview.png")

# prova de legibilidade 48px (downscale NN do master)
$master = [System.Drawing.Image]::FromFile("$PSScriptRoot\icon-master-1024.png")
$s48 = New-Object System.Drawing.Bitmap 48, 48
$g = [System.Drawing.Graphics]::FromImage($s48)
$g.InterpolationMode = 'NearestNeighbor'
$g.PixelOffsetMode = 'Half'
$g.DrawImage($master, 0, 0, 48, 48)
$g.Dispose(); $master.Dispose()
$s48.Save("$PSScriptRoot\icon-silhouette-test-48.png")

Write-Output 'pixel icon 32x32 -> variantes 1024 geradas'
