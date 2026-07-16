# CANGA-17 — downscale das artes 1024 para os 42 mipmaps Android.
# Chamado por generate-android-mipmaps.sh; pode rodar sozinho se os PNGs 1024 existirem.
$ErrorActionPreference = 'Stop'
Add-Type -AssemblyName System.Drawing
Set-Location $PSScriptRoot
$res = Join-Path $PSScriptRoot '..\..\android\src'

function Save-Scaled([string]$srcPath, [string]$destPath, [int]$size) {
    $src = [System.Drawing.Image]::FromFile((Resolve-Path $srcPath))
    $dst = New-Object System.Drawing.Bitmap $size, $size
    $g = [System.Drawing.Graphics]::FromImage($dst)
    $g.InterpolationMode = 'HighQualityBicubic'
    $g.SmoothingMode = 'HighQuality'
    $g.PixelOffsetMode = 'HighQuality'
    $g.DrawImage($src, 0, 0, $size, $size)
    $g.Dispose(); $src.Dispose()
    $full = Join-Path $res $destPath
    $dst.Save($full, [System.Drawing.Imaging.ImageFormat]::Png)
    $dst.Dispose()
    Write-Output ("{0} ({1}px)" -f $destPath, $size)
}

$legacy   = @{ 'ldpi' = 36; 'mdpi' = 48; 'hdpi' = 72; 'xhdpi' = 96; 'xxhdpi' = 144; 'xxxhdpi' = 192 }
$adaptive = @{ 'mdpi' = 108; 'hdpi' = 162; 'xhdpi' = 216; 'xxhdpi' = 324; 'xxxhdpi' = 432 }

foreach ($d in $legacy.Keys) {
    Save-Scaled 'icon-master-1024.png'       "main\res\mipmap-$d\ic_launcher.png"  $legacy[$d]
    Save-Scaled 'icon-master-debug-1024.png' "debug\res\mipmap-$d\ic_launcher.png" $legacy[$d]
}
foreach ($d in $adaptive.Keys) {
    $s = $adaptive[$d]
    Save-Scaled 'icon-adaptive-foreground.png'       "main\res\mipmap-$d\ic_launcher_foreground.png"  $s
    Save-Scaled 'icon-adaptive-foreground.png'       "debug\res\mipmap-$d\ic_launcher_foreground.png" $s
    Save-Scaled 'icon-adaptive-background.png'       "main\res\mipmap-$d\ic_launcher_background.png"  $s
    Save-Scaled 'icon-adaptive-background-debug.png' "debug\res\mipmap-$d\ic_launcher_background.png" $s
    Save-Scaled 'icon-adaptive-monochrome.png'       "main\res\mipmap-$d\ic_launcher_monochrome.png"  $s
    Save-Scaled 'icon-adaptive-monochrome.png'       "debug\res\mipmap-$d\ic_launcher_monochrome.png" $s
}
Write-Output 'mipmaps gerados'
