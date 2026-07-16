# Checklist: espelhamento GitLab → GitHub por release (E4)

> Estado: mecanismo documentado, **espelhamento de branch executado com sucesso em 2026-07-15** (`git push github main`, fast-forward, SHA confirmado via API, zero tags vazaram) e **espelhamento de tag exercitado ponta-a-ponta em 2026-07-16** no ensaio do pipeline de CD (E9, tag `v0.0.1-rc2`): só a tag exata chegou ao GitHub, main por fast-forward, nenhuma tag herdada vazou. O mirror está **automatizado** no job manual `mirror:github` do `.gitlab-ci.yml` — este checklist permanece como referência do mecanismo e para execução manual excepcional.
>
> **Decisão de negócio (2026-07-16)**: builds são **vendidas** — binários ficam restritos aos artefatos do GitLab (projeto privado). O GitHub é somente espelho de código-fonte: **nenhuma Release, nenhum asset é publicado lá**. O updates checker in-game (A2) fica dormente até existir um canal de updates próprio.

## Achado importante: tags `v*` não distinguem fork de upstream

As **96 tags herdadas do SPD upstream** (`v0.1.0` até `v3.3.8`) **já seguem o padrão `v*`** — o mesmo padrão decidido para as tags de release do próprio fork (`v0.1.0+spd3.3.8` etc., ver `appVersionName` no `build.gradle` raiz). Um filtro por glob (`git push github 'refs/tags/v*'` ou `git push github --tags`) **vazaria as 96 tags herdadas para o repositório público**, violando diretamente o critério de aceite do E4 ("jamais as centenas herdadas do SPD").

**Mecanismo correto**: nunca espelhar tags por padrão/glob. Espelhar **apenas a tag exata que disparou o release**, referenciada explicitamente:

```bash
# NUNCA: git push github --tags
# NUNCA: git push github 'refs/tags/v*'

# SEMPRE: a tag específica do release em questão
git push github refs/tags/<tag-exata>:refs/tags/<tag-exata>
```

No pipeline de CD (E9), isso é natural: o job roda no contexto de `$CI_COMMIT_TAG` (a variável do GitLab CI já contém o nome exato da tag que disparou o pipeline) — nunca um glob.

## Checklist manual (primeira execução / validação do mecanismo)

1. **Branch**: `git push github main` — fast-forward apenas, nunca `--force`. Verificar antes com `git merge-base --is-ancestor github/main main` (se não for ancestor, PARAR e investigar — não forçar).
2. **Tag** (só quando houver uma tag de release real do fork para espelhar): `git push github refs/tags/<tag-exata>:refs/tags/<tag-exata>` — nome explícito, nunca glob.
3. **Verificar** no GitHub: branch `main` atualizado, só a tag esperada apareceu (nenhuma das 96 herdadas vazou), e **zero Releases/assets** — binários jamais saem do GitLab (decisão de negócio de 2026-07-16; o item antigo de "publicar Release com artefatos" foi removido deste checklist por essa razão).

## Credenciais

- **Uso manual/interativo** (como este checklist, quando rodado por uma pessoa): as credenciais já autenticadas do ambiente (`gh`/git credential manager) servem.
- **Uso em CI/pipeline** (E9): PAT **fine-grained**, escopo só `contents:write` no repo `sandinocoelho/judgement-of-the-cangaceiro`, guardada como variável mascarada/Secure File no GitLab — nunca a credencial de desenvolvedor interativa.

## Por que não espelhar continuamente

Decisão registrada: o espelhamento roda **só em tags de release** (job de pipeline), não é o push-mirror nativo do GitLab (que espelharia continuamente e vazaria histórico de desenvolvimento — branches de feature, `upstream-sync/*` etc. — para o repositório público antes de estarem prontos).
