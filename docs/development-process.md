# Processo de desenvolvimento — Judgement of the Cangaceiro

> Ticket: E6/CANGA-27. Consolida o que E2 (remotes), E4 (mirror por release) e E5 (política de branching + proteções) definiram. Onde algo ainda não foi implementado (marcado **[PENDENTE]** abaixo), este documento registra o desenho já decidido, não uma garantia de que já funciona.

## Mapa de plataformas

| Plataforma | Papel | Visibilidade |
|---|---|---|
| **GitLab** (`gitlab.com/cloud.sandino/judgement-of-the-cangaceiro`) | Desenvolvimento primário: branches, MRs, CI/CD (E8/E9), issues internas | Privado |
| **GitHub** (`github.com/sandinocoelho/judgement-of-the-cangaceiro`) | Distribuição pública: espelho de `main` nos releases estáveis, GitHub Releases com artefatos (consumido pelo serviço de updates in-game, ver A2/`GitHubUpdates`) | Público (GPLv3 — fonte precisa ficar acessível) |
| **Upstream** (`github.com/00-Evan/shattered-pixel-dungeon`) | Fonte original do SPD, só para sincronizar melhorias/correções do jogo base | Público, não é nosso |

O GitLab nunca conhece o `upstream` — a sincronização com o SPD passa exclusivamente pela máquina local (branch `upstream-sync/spd-x.y.z`, ver exceção abaixo).

## Papéis dos 3 remotes (visão do clone local)

- **`origin` → GitLab.** Onde o trabalho do dia a dia acontece: push de feature branches, abertura de MR, merge para `main`.
- **`github` → GitHub (fork público).** Só recebe push do job de mirror por tag de release **[PENDENTE — mecanismo de E4]**; nunca de push manual de desenvolvimento (branches de feature não vão pra lá).
- **`upstream` → SPD original (00-Evan).** Só fetch, nunca push. Usado para trazer commits do jogo base via `upstream-sync/spd-x.y.z`.

## Fluxo padrão: card → release

1. **Card** (ticket no Jira, projeto CANGA) definido e priorizado.
2. **Branch** curta a partir de `main`, naming `<ticket>-descricao` (ex.: `CANGA-27-development-process`) — ver `docs/branching-policy.md`.
3. **MR** para `main` usando o template (`docs/PULL_REQUEST_TEMPLATE.md`) com o checklist objetivo preenchido.
4. **Gate antes do merge**: pipeline verde no CI **[PENDENTE — CI ainda não existe, ver E8]**; até o CI existir, o gate é build local + checklist do template preenchido manualmente.
5. **Merge**: só via MR (push direto em `main` está bloqueado pela proteção de branch do E5) — auto-merge com checklist para dev solo, sem revisor externo.
6. **Release = tag `vX.Y.Z` na `main`.** A tag protegida dispara o pipeline de release **[PENDENTE — ver E9]**: build assinado → mapping R8 arquivado → gate manual → Release no GitHub com artefatos → mirror de `main`+tag pro GitHub (mecanismo do E4, ver abaixo).

## Espelhamento GitLab → GitHub por release (E4)

**[PENDENTE DE IMPLEMENTAÇÃO — E4/CANGA-25 ainda não foi executado neste momento; esta seção documenta o desenho já decidido, a ser validado quando E4 rodar.]**

Desenho decidido:
- O espelhamento é um **job de pipeline disparado por tag de release** (`v*`), não o push-mirror nativo do GitLab (que espelharia continuamente e vazaria histórico de desenvolvimento não estável pro repo público).
- Credencial: PAT (Personal Access Token) **fine-grained**, escopo só `contents:write` no repo do GitHub.
- **Nunca `--force`** no push pro GitHub.
- Só as tags **próprias do fork** (`v*`) são espelhadas — nunca as centenas de tags herdadas do SPD upstream.
- A Release do GitHub com artefatos binários continua sendo publicada nesse fluxo — é um hard dependency: o serviço de updates in-game (`GitHubUpdates`, ticket A2) consome `api.github.com/repos/sandinocoelho/judgement-of-the-cangaceiro/releases`. Quebrar esse endpoint quebra o update in-game.

## Exceção: sincronização com o upstream

Documentada em detalhe em `docs/branching-policy.md`. Resumo: branch `upstream-sync/spd-x.y.z`, pode viver mais que 1–2 dias, mas só mergeia em `main` com o grep de regressão (`com.shatteredpixel` em `*.java`/`*.gradle`/`*.pro`/`*.xml` limpo, exceto headers GPL e aliases de compat intencionais) passando.

## Template de MR

`docs/PULL_REQUEST_TEMPLATE.md` foi reescrito (era o aviso padrão do upstream de "não aceitamos PRs", que não se aplica ao nosso fluxo interno no GitLab) para um checklist objetivo — ver arquivo.
