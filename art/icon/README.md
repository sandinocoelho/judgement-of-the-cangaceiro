# Ícone base/master (CANGA-16)

Conceito A aprovado em 2026-07-15: **chapéu de couro de meia-lua de Lampião
com estrela de seis pontas**, estilo flat com contorno forte, paleta de couro
sobre fundo de sertão (terracota).

## Arquivos

| Arquivo | Papel |
|---|---|
| `hat-shapes.svg.fragment` | **Fonte da verdade** do desenho do chapéu — edite aqui |
| `generate-icons.sh` | Gera os SVGs das variantes e renderiza os PNGs (Edge headless + .NET p/ downscale) |
| `icon-master.svg` / `icon-master-1024.png` | Arte master 1024px, full-bleed quadrado (lojas aplicam a própria máscara de canto) |
| `icon-adaptive-foreground.{svg,png}` | Foreground do adaptive icon Android — conteúdo dentro da zona segura de 66% |
| `icon-adaptive-background.{svg,png}` | Background do adaptive icon (gradiente sertão + disco de sol) |
| `icon-adaptive-monochrome.{svg,png}` | Camada monochrome (Android 13+ themed icons): silhueta branca, estrela vazada — só o alpha é usado |
| `icon-adaptive-monochrome-preview.png` | Apenas para avaliação humana (silhueta sobre fundo colorido) |
| `icon-silhouette-test-48.png` | Prova de legibilidade a 48px (critério de aceite) |

## Regenerar

```bash
cd art/icon && bash generate-icons.sh
```

Os SVGs de variantes são gerados a partir do fragment — nunca edite os SVGs
gerados diretamente.

## Consumo

- **C2 (Android)**: foreground/background/monochrome → mipmap adaptive icons
- **C3 (Desktop)**: master → icon_16..256.png, windows.ico, mac.icns
- **C4 (iOS)**: master → conjunto de ícones + splash
