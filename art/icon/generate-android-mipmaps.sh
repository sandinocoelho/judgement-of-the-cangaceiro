#!/usr/bin/env bash
# CANGA-17 — gera os 42 mipmaps Android a partir das artes 1024 do C1.
# O conjunto debug usa fundo azul-noite para distinguir builds INDEV no launcher.
# Uso (Git Bash, na pasta art/icon):  ./generate-android-mipmaps.sh
set -euo pipefail

cd "$(dirname "$0")"
EDGE="/c/Program Files (x86)/Microsoft/Edge/Application/msedge.exe"
HAT=$(cat hat-shapes.svg.fragment)

# --- variantes debug (fundo azul-noite do sertão) ---
cat > icon-master-debug.svg << SVG
<svg xmlns="http://www.w3.org/2000/svg" width="1024" height="1024" viewBox="0 0 1024 1024">
  <defs>
    <radialGradient id="bg" cx="50%" cy="42%" r="75%">
      <stop offset="0%" stop-color="#46608C"/>
      <stop offset="70%" stop-color="#2C3E63"/>
      <stop offset="100%" stop-color="#1B2743"/>
    </radialGradient>
  </defs>
  <rect width="1024" height="1024" fill="url(#bg)"/>
  <circle cx="512" cy="470" r="365" fill="#8FA6CC" opacity="0.30"/>
  $HAT
</svg>
SVG

cat > icon-adaptive-background-debug.svg << SVG
<svg xmlns="http://www.w3.org/2000/svg" width="1024" height="1024" viewBox="0 0 1024 1024">
  <defs>
    <radialGradient id="bg" cx="50%" cy="42%" r="75%">
      <stop offset="0%" stop-color="#46608C"/>
      <stop offset="70%" stop-color="#2C3E63"/>
      <stop offset="100%" stop-color="#1B2743"/>
    </radialGradient>
  </defs>
  <rect width="1024" height="1024" fill="url(#bg)"/>
  <circle cx="512" cy="470" r="365" fill="#8FA6CC" opacity="0.30"/>
</svg>
SVG

render() {
  "$EDGE" --headless --disable-gpu --no-first-run --force-device-scale-factor=1 \
    --default-background-color="$3" --window-size=1024,1024 \
    --screenshot="$(cygpath -w "$PWD/$2")" "file:///$(cygpath -m "$PWD/$1")" 2>/dev/null
  echo "renderizado: $2"
}
render icon-master-debug.svg              icon-master-debug-1024.png          00000000
render icon-adaptive-background-debug.svg icon-adaptive-background-debug.png  FFFFFFFF

# --- downscale .NET para todas as densidades ---
powershell.exe -NoProfile -File ./generate-android-mipmaps.ps1
echo "concluido"
