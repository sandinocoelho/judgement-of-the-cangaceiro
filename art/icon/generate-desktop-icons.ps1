# CANGA-18 — gera os ícones desktop a partir de icon-master-1024.png:
# icon_16/32/48/64/128/256.png + windows.ico + mac.icns (entradas PNG).
# Uso: powershell -File generate-desktop-icons.ps1  (na pasta art/icon)
$ErrorActionPreference = 'Stop'
Add-Type -AssemblyName System.Drawing
Set-Location $PSScriptRoot
$iconsDir = Join-Path $PSScriptRoot '..\..\desktop\src\main\assets\icons'

function Get-ScaledPngBytes([System.Drawing.Image]$src, [int]$size) {
    $dst = New-Object System.Drawing.Bitmap $size, $size
    $g = [System.Drawing.Graphics]::FromImage($dst)
    $g.InterpolationMode = 'HighQualityBicubic'
    $g.SmoothingMode = 'HighQuality'
    $g.PixelOffsetMode = 'HighQuality'
    $g.DrawImage($src, 0, 0, $size, $size)
    $g.Dispose()
    $ms = New-Object System.IO.MemoryStream
    $dst.Save($ms, [System.Drawing.Imaging.ImageFormat]::Png)
    $dst.Dispose()
    # a vírgula impede o PowerShell de enumerar o byte[] no retorno (viraria
    # Object[] e o BinaryWriter ligaria na overload errada)
    return , $ms.ToArray()
}

$master = [System.Drawing.Image]::FromFile((Resolve-Path 'icon-master-1024.png'))
$sizes = 16, 32, 48, 64, 128, 256
$png = @{}
foreach ($s in $sizes) { $png[$s] = Get-ScaledPngBytes $master $s }
$png[1024] = [System.IO.File]::ReadAllBytes((Resolve-Path 'icon-master-1024.png'))
$master.Dispose()

# --- PNGs individuais (nomes idênticos aos do vanilla) ---
foreach ($s in $sizes) {
    [System.IO.File]::WriteAllBytes((Join-Path $iconsDir "icon_$s.png"), $png[$s])
    Write-Output "icon_$s.png"
}

# --- windows.ico: ICONDIR + ICONDIRENTRYs + blobs PNG (suportado no Vista+) ---
$ms = New-Object System.IO.MemoryStream
$w = New-Object System.IO.BinaryWriter($ms)
$w.Write([UInt16]0); $w.Write([UInt16]1); $w.Write([UInt16]$sizes.Count)
$offset = 6 + 16 * $sizes.Count
foreach ($s in $sizes) {
    $dim = if ($s -ge 256) { 0 } else { $s }   # 0 = 256 no formato ICO
    $w.Write([Byte]$dim); $w.Write([Byte]$dim)
    $w.Write([Byte]0); $w.Write([Byte]0)
    $w.Write([UInt16]1); $w.Write([UInt16]32)
    $w.Write([UInt32]$png[$s].Length); $w.Write([UInt32]$offset)
    $offset += $png[$s].Length
}
foreach ($s in $sizes) { $w.Write([byte[]]$png[$s]) }
$w.Flush()
[System.IO.File]::WriteAllBytes((Join-Path $iconsDir 'windows.ico'), $ms.ToArray())
$w.Dispose()
Write-Output 'windows.ico'

# --- mac.icns: chunks tipo+tamanho(BE) com dados PNG ---
function Get-BE([UInt32]$v) { $b = [BitConverter]::GetBytes($v); [Array]::Reverse($b); $b }
$chunkTypes = @(
    @{ type = 'icp4'; size = 16 },
    @{ type = 'icp5'; size = 32 },
    @{ type = 'icp6'; size = 64 },
    @{ type = 'ic07'; size = 128 },
    @{ type = 'ic08'; size = 256 },
    @{ type = 'ic10'; size = 1024 }   # 512@2x
)
$chunks = New-Object System.IO.MemoryStream
foreach ($c in $chunkTypes) {
    $data = $png[$c.size]
    $chunks.Write([System.Text.Encoding]::ASCII.GetBytes($c.type), 0, 4)
    $len = Get-BE ([UInt32]($data.Length + 8))
    $chunks.Write($len, 0, 4)
    $chunks.Write($data, 0, $data.Length)
}
$body = $chunks.ToArray()
$out = New-Object System.IO.MemoryStream
$out.Write([System.Text.Encoding]::ASCII.GetBytes('icns'), 0, 4)
$total = Get-BE ([UInt32]($body.Length + 8))
$out.Write($total, 0, 4)
$out.Write($body, 0, $body.Length)
[System.IO.File]::WriteAllBytes((Join-Path $iconsDir 'mac.icns'), $out.ToArray())
Write-Output 'mac.icns'
Write-Output 'concluido'
