# Ícone do jogo — pixel art (CANGA-32)

Conceito A aprovado: **chapéu de couro de meia-lua de Lampião com estrela de
seis pontas**. A arte é **pixel art** (regra do projeto: o jogo é pixel art —
nada de vetor liso/gradientes): sprite autoral de 32×32, ampliado por
nearest-neighbor em fatores inteiros.

## Arquivos

| Arquivo | Papel |
|---|---|
| `generate-pixel-icon.ps1` | **Fonte da verdade**: o grid 32×32 vive dentro dele; gera todas as artes 1024 |
| `icon-master-1024.png` | Master release (chapéu 28×, fundo terracota com dither) |
| `icon-master-debug-1024.png` | Master debug (fundo azul-noite — INDEV distinguível) |
| `icon-adaptive-foreground.png` | Foreground adaptive Android (chapéu 21× = 672px, dentro da zona segura de 66%) |
| `icon-adaptive-background.png` / `-debug.png` | Backgrounds full-bleed (release/debug) |
| `icon-adaptive-monochrome.png` | Camada monochrome (Android 13+): silhueta branca, estrela vazada — só alpha |
| `icon-adaptive-monochrome-preview.png` | Só para avaliação humana |
| `icon-silhouette-test-48.png` | Prova de legibilidade a 48px |
| `generate-android-mipmaps.ps1` | Downscale dos 1024 para os 42 mipmaps de `android/src/{main,debug}/res` |
| `generate-desktop-icons.ps1` | Downscale + empacotamento de `icon_16..256.png`, `windows.ico`, `mac.icns` |

## Regenerar tudo

```powershell
cd art/icon
powershell -File generate-pixel-icon.ps1        # grid 32x32 -> artes 1024
powershell -File generate-android-mipmaps.ps1   # -> android/src/**/res/mipmap-*
powershell -File generate-desktop-icons.ps1     # -> desktop/src/main/assets/icons/
```

Para editar a arte: mude os caracteres do grid `$rows` no
`generate-pixel-icon.ps1` (legenda da paleta no próprio script; o validador
acusa linha/caractere inválido) e rode os três passos acima.

> Histórico: a primeira iteração (placeholder) era vetorial/flat, gerada por um
> pipeline SVG+Edge removido no CANGA-32 — está no histórico do git se um dia
> for útil.
