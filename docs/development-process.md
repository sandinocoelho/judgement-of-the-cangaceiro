# Processo de desenvolvimento — Judgement of the Cangaceiro

> Ticket: E6/CANGA-27. Consolida o que E2 (remotes), E4 (mirror por release), E5 (política de branching + proteções), E8 (CI de MR) e E9 (CD por tag) definiram — **tudo implementado e validado em 2026-07-16** (o E9 foi ensaiado ponta-a-ponta com uma tag de teste antes da primeira release real).

## Mapa de plataformas

| Plataforma | Papel | Visibilidade |
|---|---|---|
| **GitLab** (`gitlab.com/cloud.sandino/judgement-of-the-cangaceiro`) | Desenvolvimento primário (branches, MRs, CI/CD) **e distribuição interna**: binários de release ficam como artefatos permanentes dos pipelines de tag | Privado |
| **GitHub** (`github.com/sandinocoelho/judgement-of-the-cangaceiro`) | **Espelho público de código-fonte apenas** (obrigação GPLv3): `main` + tags de release próprias. **Nenhuma Release, nenhum binário** — builds são vendidas (decisão de negócio, 2026-07-16) | Público |
| **Upstream** (`github.com/00-Evan/shattered-pixel-dungeon`) | Fonte original do SPD, só para sincronizar melhorias/correções do jogo base | Público, não é nosso |

O GitLab nunca conhece o `upstream` — a sincronização com o SPD passa exclusivamente pela máquina local (branch `upstream-sync/spd-x.y.z`, ver exceção abaixo).

> Nota (A2): o serviço de updates in-game (`GitHubUpdates`) aponta para o endpoint de releases do GitHub, que fica **vazio de propósito** — o jogo responde "sem update" graciosamente. Repontar updates para um canal próprio é decisão futura de produto.

## Papéis dos 3 remotes (visão do clone local)

- **`origin` → GitLab.** Onde o trabalho do dia a dia acontece: push de feature branches, abertura de MR, merge para `main`.
- **`github` → GitHub (espelho público).** Só recebe push do job manual `mirror:github` do pipeline de tag (tag exata + `main` fast-forward — mecanismo do E4, ver `docs/release-mirror-checklist.md`); nunca push manual de desenvolvimento.
- **`upstream` → SPD original (00-Evan).** Só fetch, nunca push. Usado para trazer commits do jogo base via `upstream-sync/spd-x.y.z`.

## Fluxo padrão: card → release

1. **Card** (ticket no Jira, projeto CANGA) definido e priorizado.
2. **Branch** curta a partir de `main`, naming `<ticket>-descricao` (ex.: `canga-27-development-process`) — ver `docs/branching-policy.md`.
3. **MR** para `main` usando o template (`docs/PULL_REQUEST_TEMPLATE.md`) com o checklist objetivo preenchido.
4. **Gate antes do merge**: pipeline verde no CI (E8): `android:assembleDebug` + `desktop:debugSmoke` (jogo abre sob Xvfb) obrigatórios; `android:lint` roda como `allow_failure` (não bloqueia). Roda em todo MR e em push na `main`.
5. **Merge**: só via MR (push direto em `main` está bloqueado pela proteção de branch do E5) — auto-merge com checklist para dev solo, sem revisor externo.
6. **Release = tag `vX.Y.Z` na `main`** (tag protegida). O pipeline de CD (E9) dispara: `android:assembleReleaseSigned` (keystore via Secure Files, ver `docs/release-signing.md`; APK assinado + **mapping R8 como artefato permanente**) e `desktop:releaseArtifacts` (jar universal + jpackage Linux) → **gate manual** → `mirror:github` (só código: tag exata + `main` fast-forward). Binários permanecem como artefatos privados no GitLab. Pipeline verde **não** substitui o smoke manual do APK R8 (regra do B4).

## Espelhamento GitLab → GitHub por release (E4)

Implementado e validado (ensaio ponta-a-ponta em 2026-07-16, detalhes em `docs/release-mirror-checklist.md`):
- Job de pipeline **manual** disparável só em tag de release (`v*`) — não é o push-mirror nativo do GitLab (que espelharia continuamente e vazaria histórico de desenvolvimento).
- Credencial: PAT **fine-grained**, escopo só `contents:write`, como variável masked+protected no GitLab.
- **Nunca `--force`** — push não-fast-forward falha o job para investigação manual.
- Só a **tag exata** que disparou o pipeline é espelhada (`$CI_COMMIT_TAG`) — nunca glob; as 96 tags herdadas do SPD jamais vazam.
- **Nenhuma Release/binário é publicado no GitHub** (decisão de negócio, 2026-07-16).

## Exceção: sincronização com o upstream

Documentada em detalhe em `docs/branching-policy.md`. Resumo: branch `upstream-sync/spd-x.y.z`, pode viver mais que 1–2 dias, mas só mergeia em `main` com o grep de regressão (`com.shatteredpixel` em `*.java`/`*.gradle`/`*.pro`/`*.xml` limpo, exceto headers GPL e aliases de compat intencionais) passando.

## Template de MR

`docs/PULL_REQUEST_TEMPLATE.md` foi reescrito (era o aviso padrão do upstream de "não aceitamos PRs", que não se aplica ao nosso fluxo interno no GitLab) para um checklist objetivo — ver arquivo.
