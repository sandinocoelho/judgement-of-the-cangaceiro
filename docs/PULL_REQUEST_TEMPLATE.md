## O que este MR faz

<!-- Descrição curta + link do ticket Jira (ex.: CANGA-27) -->

## Checklist objetivo

- [ ] Build local passou (`./gradlew <módulo>:compileJava` ou equivalente para a(s) plataforma(s) afetada(s))
- [ ] Mexeu em serialização/FQCN (classes salvas via `Bundle`, aliases de compat em `ShatteredPixelDungeon.java`)? Se sim, risco de quebrar saves — validado?
- [ ] Mexeu em `proguard-rules.pro` ou em algo que o R8 precisa proteger (`-keepnames`)? Se sim, revisado?
- [ ] Versão (`appVersionCode`/`appVersionName` no `build.gradle` raiz) está coerente com a mudança, se aplicável?
- [ ] Se a branch é `upstream-sync/spd-x.y.z`: grep de regressão por `com.shatteredpixel` limpo (só headers GPL e aliases/URLs intencionais) — ver `docs/branching-policy.md`

## Plataformas afetadas

- [ ] Android
- [ ] iOS *(adiado — fora do escopo desde 2026-07-16; marcar só se tocar o módulo mesmo assim)*
- [ ] Desktop
- [ ] Cross-platform/Core (todas)
