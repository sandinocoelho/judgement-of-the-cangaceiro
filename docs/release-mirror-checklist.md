# Checklist: espelhamento GitLab → GitHub por release (E4)

> Estado: mecanismo documentado e **primeiro espelhamento de branch executado com sucesso em 2026-07-15** (`git push github main`, fast-forward `7b8b845a7..ecf2579b1`, confirmado via API: SHA do GitHub bate com o local, zero tags vazaram). Espelhamento de **tag** ainda não foi exercitado — só faz sentido quando existir uma tag de release real do fork (depende de D1/E9).

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
3. **Verificar** no GitHub: branch `main` atualizado, só a tag esperada apareceu (nenhuma das 96 herdadas vazou).
4. **Release do GitHub com artefatos**: publicar via `gh release create <tag-exata> <artefatos...>` (ou pela automação do E9 quando existir). Esse é o endpoint que o serviço de updates in-game (`GitHubUpdates`, ticket A2) consome — confirmar que a Release aparece em `api.github.com/repos/sandinocoelho/judgement-of-the-cangaceiro/releases` depois de publicada.

## Credenciais

- **Uso manual/interativo** (como este checklist, quando rodado por uma pessoa): as credenciais já autenticadas do ambiente (`gh`/git credential manager) servem.
- **Uso em CI/pipeline** (E9): PAT **fine-grained**, escopo só `contents:write` no repo `sandinocoelho/judgement-of-the-cangaceiro`, guardada como variável mascarada/Secure File no GitLab — nunca a credencial de desenvolvedor interativa.

## Por que não espelhar continuamente

Decisão registrada: o espelhamento roda **só em tags de release** (job de pipeline), não é o push-mirror nativo do GitLab (que espelharia continuamente e vazaria histórico de desenvolvimento — branches de feature, `upstream-sync/*` etc. — para o repositório público antes de estarem prontos).
