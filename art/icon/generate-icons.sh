#!/usr/bin/env bash
# CANGA-16 — gera as variantes do ícone a partir de hat-shapes.svg.fragment.
# Uso (Git Bash, na pasta art/icon):  ./generate-icons.sh
# Requer apenas o Microsoft Edge (renderização headless).
set -euo pipefail

cd "$(dirname "$0")"
EDGE="/c/Program Files (x86)/Microsoft/Edge/Application/msedge.exe"
HAT=$(cat hat-shapes.svg.fragment)

# --- master 1024 (fundo sertão + disco de sol + chapéu) ---
cat > icon-master.svg << SVG
<svg xmlns="http://www.w3.org/2000/svg" width="1024" height="1024" viewBox="0 0 1024 1024">
  <defs>
    <radialGradient id="bg" cx="50%" cy="42%" r="75%">
      <stop offset="0%" stop-color="#D8703F"/>
      <stop offset="70%" stop-color="#A94A2C"/>
      <stop offset="100%" stop-color="#7E3520"/>
    </radialGradient>
  </defs>
  <!-- full-bleed quadrado: launchers/lojas aplicam a própria máscara de canto -->
  <rect width="1024" height="1024" fill="url(#bg)"/>
  <circle cx="512" cy="470" r="365" fill="#E9A05C" opacity="0.35"/>
  $HAT
</svg>
SVG

# --- adaptive foreground (transparente; conteúdo já cabe na zona segura de 66%) ---
cat > icon-adaptive-foreground.svg << SVG
<svg xmlns="http://www.w3.org/2000/svg" width="1024" height="1024" viewBox="0 0 1024 1024">
  $HAT
</svg>
SVG

# --- adaptive background (sem chapéu) ---
cat > icon-adaptive-background.svg << SVG
<svg xmlns="http://www.w3.org/2000/svg" width="1024" height="1024" viewBox="0 0 1024 1024">
  <defs>
    <radialGradient id="bg" cx="50%" cy="42%" r="75%">
      <stop offset="0%" stop-color="#D8703F"/>
      <stop offset="70%" stop-color="#A94A2C"/>
      <stop offset="100%" stop-color="#7E3520"/>
    </radialGradient>
  </defs>
  <rect width="1024" height="1024" fill="url(#bg)"/>
  <circle cx="512" cy="470" r="365" fill="#E9A05C" opacity="0.35"/>
</svg>
SVG

# --- monochrome 1-bit (silhueta branca, estrela vazada via máscara) ---
cat > icon-adaptive-monochrome.svg << SVG
<svg xmlns="http://www.w3.org/2000/svg" width="1024" height="1024" viewBox="0 0 1024 1024">
  <defs>
    <mask id="starcut">
      <rect width="1024" height="1024" fill="white"/>
      <g transform="translate(512 360)">
        <path d="M 0 -74 L 21 -37 L 64 -37 L 43 0 L 64 37 L 21 37 L 0 74 L -21 37 L -64 37 L -43 0 L -64 -37 L -21 -37 Z"
              fill="black" stroke="black" stroke-width="10" stroke-linejoin="round"/>
      </g>
    </mask>
  </defs>
  <style>#hat, #hat * { fill: #FFFFFF; stroke: #FFFFFF; }</style>
  <g mask="url(#starcut)">
  $HAT
  </g>
</svg>
SVG

render() { # render <svg> <png> <tamanho> <bg-hex8>
  "$EDGE" --headless --disable-gpu --no-first-run --force-device-scale-factor=1 \
    --default-background-color="$4" --window-size="$3,$3" \
    --screenshot="$(cygpath -w "$PWD/$2")" "file:///$(cygpath -m "$PWD/$1")" 2>/dev/null
  echo "renderizado: $2 (${3}px)"
}

render icon-master.svg               icon-master-1024.png          1024 00000000
render icon-adaptive-foreground.svg  icon-adaptive-foreground.png  1024 00000000
render icon-adaptive-background.svg  icon-adaptive-background.png  1024 FFFFFFFF
render icon-adaptive-monochrome.svg  icon-adaptive-monochrome.png  1024 00000000
# preview do monochrome sobre fundo escuro (só para avaliação humana)
render icon-adaptive-monochrome.svg  icon-adaptive-monochrome-preview.png 1024 FF20242C

# teste de silhueta a 48px: Edge não abre janela tão pequena, então o
# downscale é feito via .NET (System.Drawing) a partir do master 1024
powershell.exe -NoProfile -Command "
  Add-Type -AssemblyName System.Drawing
  \$src = [System.Drawing.Image]::FromFile((Join-Path (Get-Location) 'icon-master-1024.png'))
  \$dst = New-Object System.Drawing.Bitmap 48, 48
  \$g = [System.Drawing.Graphics]::FromImage(\$dst)
  \$g.InterpolationMode = 'HighQualityBicubic'
  \$g.DrawImage(\$src, 0, 0, 48, 48)
  \$g.Dispose(); \$src.Dispose()
  \$dst.Save((Join-Path (Get-Location) 'icon-silhouette-test-48.png'))
  \$dst.Dispose()
"
echo "renderizado: icon-silhouette-test-48.png (48px, downscale .NET)"
echo "concluido"
