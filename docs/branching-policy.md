# Política de branching — Judgement of the Cangaceiro

> Consolidado no processo de desenvolvimento completo em `docs/development-process.md` (ticket E6) quando esse documento existir. Este arquivo é a fonte da política em si.

## Regra central: trunk-based development

- **`main` está sempre em estado releaseável.** Nunca fica quebrada, nunca fica com trabalho incompleto merged.
- **Sem branches longas de `develop` ou `release`.** Todo trabalho nasce de `main` e volta pra `main`.
- **Feature branches são curtas**: no máximo 1–2 dias de vida. Se uma tarefa não cabe nisso, ela é grande demais — quebre em tarefas menores (ver critério de estimativa do `po`).
- **Naming**: `<ticket>-descricao-curta` (ex.: `CANGA-26-branch-policy`). O prefixo do ticket Jira facilita rastrear qual branch corresponde a qual item do backlog.
- **MRs pequenos.** Cada MR corresponde a um ticket (ou a um recorte claro dele). Evite MRs que misturam múltiplos tickets sem relação.
- **Release = tag na `main`.** Não existe branch de release separada; uma tag `vX.Y.Z` na `main` É o release (ver E9, pipeline CD por tag).

## Fluxo padrão

1. Cria branch a partir de `main` atualizada, seguindo o naming acima.
2. Trabalha, commita.
3. Abre MR pra `main`. Para dev solo, o "review" é o checklist objetivo do template de MR (ver `docs/PULL_REQUEST_TEMPLATE.md` e, quando existir, `docs/development-process.md`/E6): build local passou? mexeu em serialização/FQCN (risco de save-break, ver B3)? regras de R8 revisadas se aplicável? versão coerente?
4. Pipeline verde (quando o CI existir — ver E8) ou build local como gate manual até lá.
5. Merge via MR (nunca push direto — ver proteções abaixo).
6. Apaga a feature branch depois do merge.

## Exceção formal: `upstream-sync/spd-x.y.z`

Sincronizar com o upstream (`00-Evan/shattered-pixel-dungeon`) é a única exceção documentada à regra de branches curtas — o merge de uma versão nova do SPD pode levar mais que 1–2 dias para resolver conflitos e revalidar o rename de package.

- **Naming obrigatório**: `upstream-sync/spd-x.y.z` (ex.: `upstream-sync/spd-3.4.0`).
- **Gate obrigatório antes do merge para `main`**: grep de regressão por `com.shatteredpixel` em `*.java`, `*.gradle`, `*.pro`, `*.xml`. Só podem sobrar headers de licença GPL e aliases/URLs intencionais (ver `ShatteredPixelDungeon.java:51-54`, os aliases de compat de save — strings que devem permanecer com o FQCN histórico do upstream, ver gotcha registrada durante o B2/B3).
- Essa branch pode viver mais que 1–2 dias enquanto os conflitos são resolvidos, mas não pode ser mergeada em `main` sem o grep limpo.

## Proteções ativas no GitLab (projeto `cloud.sandino/judgement-of-the-cangaceiro`)

Configuradas via API em 2026-07-15 (ticket E5):

- **Branch `main`**: push direto bloqueado (`push_access_level = No one`) — todo merge passa por MR; `allow_force_push = false`; merge autorizado para Maintainer.
- **Tags `v*`**: protegidas — só Maintainer pode criar tags que casam com o padrão `v*` (as tags do fork; nunca as centenas herdadas do SPD, ver decisão em E4).

## O que fica fora deste documento

- Papéis dos 3 remotes (`origin`/`github`/`upstream`) e o mapa completo de plataformas — consolidado em `docs/development-process.md` (E6).
- Configuração do pipeline de CI que valida esses MRs — E8.
- Runbook de release por tag — E9.
