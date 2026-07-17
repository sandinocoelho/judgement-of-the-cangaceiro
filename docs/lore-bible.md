# Lore Bible — Judgement of the Cangaceiro

> Documento de referência narrativa. Criado a partir de um levantamento do estado atual do repositório (todo o conteúdo de texto do jogo, arquivo por arquivo). Serve como ponto de partida para o trabalho do agente `narrative-designer` antes de qualquer retematização em volume.

## 0. Estado atual — leia isto primeiro

**Nenhuma camada temática de cangaço foi aplicada ao conteúdo do jogo ainda.** Tudo o que está descrito na Parte 1 abaixo é a lore original do Shattered Pixel Dungeon (SPD), preservada 100% intacta. O rebranding feito até agora (`cloud.sandino.judgementofthecangaceiro`, nome do app, versionamento) é puramente de infraestrutura/identidade — nenhum nome de item, monstro, classe, região ou texto de diário foi reescrito.

Isso significa duas coisas para quem for usar este documento:

1. A **Parte 1** é um resumo do que existe hoje nos arquivos — útil para entender a estrutura narrativa (onde a história mora, como ela é entregue ao jogador, quais peças precisam de um equivalente temático).
2. A **Parte 2** é a direção pretendida (cangaço/sertão nordestino), hoje documentada apenas em prosa no README e no agente `narrative-designer`. Não existe ainda um glossário de nomes aprovados — a Parte 3 propõe um esqueleto para isso.

---

## Parte 1 — A história atual (base Shattered Pixel Dungeon)

### 1.1 Premissa

Um Herói sem nome desce numa masmorra em busca do **Amulet of Yendor**, um artefato de poder lendário. A masmorra tem 5 regiões (biomas), cada uma com ~5 andares, mais um confronto final:

| Ordem | Região (código) | Nome em jogo |
|---|---|---|
| 1 | `SewerLevel` | Sewers (Esgotos) |
| 2 | `PrisonLevel` | Prison (Prisão) |
| 3 | `CavesLevel` | Caves (Cavernas) |
| 4 | `CityLevel` | City (Cidade/Metrópole) |
| 5 | `HallsLevel` | Halls (Salões) |
| final | `LastLevel` | covil de Yog-Dzewa |

A narrativa de superfície (onboarding, textos de UI) é mínima — a história real é contada através de **documentos colecionáveis** (cartas e diários) encontrados pelo jogador em cada região, um sistema de "found lore" clássico de imersive sim/roguelike.

### 1.2 Os cinco documentos de lore (o coração da narrativa)

Cada região esconde um conjunto de páginas que, juntas, formam um diário. Lidos em ordem cronológica dentro da região, eles contam uma história paralela à descida do Herói — a queda da civilização anã que construiu (e agora habita) a masmorra.

**1. Esgotos — "Guard's Letters" (cartas da guarda Sonya)**
Sonya é promovida a um time especial que deveria "limpar vermes e fora-da-lei" dos esgotos — na prática, ratos gigantes e gnolls agressivos. O capitão do time, Thomas, é experiente e confiável. O grupo encontra a Guilda dos Ladrões (com quem Thomas negocia informações "por baixo do pano"). Perdidos após um combate, os dois seguem para a entrada da prisão em busca de uma rota de fuga — lá, um monstro de limo mágico mata Thomas. Sonya deixa as cartas como aviso: "não é só ratos e caranguejos gigantes, tem algo muito errado aqui embaixo."

**2. Prisão — "Warden's Journal" (diário da carcereira Kiana Smith)**
Kiana administra uma prisão subterrânea; presos começam a agir de forma inexplicável e alguns guardas falam em "assombração". Ela contrata o recruta Thomas (o mesmo dos esgotos — ele trabalhou nos dois lugares). Engenheiros da cidade selam a entrada das minas dos anões, citando vagamente "vazamento de magia anã". Sem suporte da cidade, Kiana ativa unidades de defesa antigas ("DM-100") por desespero. No fim, escreve uma carta de despedida a Thomas pedindo que tranque e reforce a cela de um prisioneiro perigoso — **Tengu** — e jogue a chave fora, então desaparece: "um capitão afunda com seu navio".

**3. Cavernas — "Explorer's Log" (diário do arquimago Archibald Drummond)**
Expedição real às minas anãs abandonadas, para prospecção de recursos e um possível projeto de prisão. Descobrem "ouro negro" (dark gold) que se degrada sob luz solar. Encontram um **troll ferreiro** que conserta equipamento em troca de ouro — e avisa que os portões da antiga metrópole anã têm máquinas de defesa perigosas. Uma dessas máquinas ataca o grupo sem provocação. Membros da expedição começam a ter os mesmos pesadelos, sem explicação. O relatório final recomenda cautela nas minas inferiores.

**4. Cidade — "Warlock's Journal" (diário de Thymor Zahir)**
Thymor é o último membro livre da corte anã. O Rei anão morre e é sucedido por um colega da corte enlouquecido pelo poder (o "Novo Rei" — revelado na seção 1.2.5 como Rodney), que escraviza a corte inteira num ritual. Thymor foge e monta uma resistência de leais ao Rei antigo, majoritariamente monges guerreiros. O ataque à câmara interna falha: o Novo Rei escraviza também os rebeldes com energia necrótica, e a cidade inteira vira uma massa de servos sem vontade própria. Ao investigar um poder ainda maior emanando dos salões mais profundos — distinto do próprio Novo Rei — Thymor testemunha algo indescritível e, apavorado, decide se entregar como "instrumento" para ajudar a conter essa entidade, que ele nomeia **Yog-Dzewa**.

**5. Salões — "???'s Journal" (o diário do próprio Novo Rei, Rodney)**
Assinado apenas "R." até a revelação final. Rodney era um estudioso cujas teorias foram rejeitadas pela corte por motivos pessoais, não técnicos. Foi sozinho "aos confins do universo" e encontrou um artefato de poder imenso, batizado por ele mesmo de **Amulet of Yendor**. Forjou um "ritual" falso para escravizar a corte e o próprio Rei, tornando-se rei. Mas sua intromissão nos planos externos atraiu a atenção de "a Coisa" — Yog-Dzewa —, que passa a forçar sua entrada no mundo exatamente onde ele guardou o amuleto. Incapaz de enfrentá-la diretamente, Rodney trava uma guerra de atrito, enviando seus súditos escravizados (e as máquinas de defesa anãs) para conter a entidade indefinidamente: *"I, RODNEY, KING OF DWARVES, WILL OUTLAST YOU YOG-DZEWA!"*

### 1.3 O artefato central — Amulet of Yendor

Origem oficialmente "desconhecida" (é o que o próprio item diz ao jogador), mas os documentos acima revelam a verdade: Rodney o encontrou, e Yog-Dzewa foi atraída por seu uso. Pegar o amuleto é a condição de vitória padrão. Existe também um desafio opcional de "Ascensão", em que carregar o amuleto de volta à superfície torna a masmorra mais perigosa — Yog tenta reconquistá-lo no processo.

### 1.4 O antagonista final — Yog-Dzewa

Um "Old God" — uma entidade cósmica de uma dimensão de "caos mágico puro" que devora mundos para se alimentar; foi atraída pelos experimentos de Rodney com magia proibida. Não consegue assumir forma física completa neste mundo; manifesta-se como um olho gigante imóvel no fundo da masmorra, protegido por avatares elementais ("punhos" de fogo, terra, decomposição, metal, luz e escuridão) e larvas que invoca para servi-la.

### 1.5 Máquinas de guerra anãs

`DM-100` / `DM-300` / Golems / Pylons — máquinas de defesa construídas pelos anões para guardar a entrada da metrópole; reaproveitadas por Rodney (e antes disso, por Kiana na prisão) como força bruta descartável. DM-300 é um chefe de região, capaz de se "supercarregar" via pilares de energia.

### 1.6 NPCs recorrentes (fora dos diários)

Ghost (fantasma triste, quest de item perdido), Wandmaker (velho fabricante de varinhas), Imp/ImpShopkeeper (duende ambicioso vendedor), Rat King, Blacksmith (o troll ferreiro das cavernas), Shopkeeper. Nenhum destes tem lore própria além de sua função de gameplay (loja/quest).

### 1.7 Mapa de arquivos — onde a história vive hoje

**Documentos de lore colecionáveis (núcleo da narrativa):**
- `core/src/main/assets/messages/journal/journal.properties` — chaves `journal.document.sewers_guard.*`, `journal.document.prison_warden.*`, `journal.document.caves_explorer.*`, `journal.document.city_warlock.*`, `journal.document.halls_king.*` (as 5 histórias da seção 1.2), e `journal.document.adventurers_guide.*` (tutorial, não é lore de mundo).
- `journal_*.properties` no mesmo diretório — traduções geridas via Transifex. **Nunca editar diretamente**; editar sempre o arquivo base e sinalizar que a tradução ficou desatualizada.

**Descrições de itens/artefatos com flavor text:**
- `core/src/main/assets/messages/items/items.properties` — `items.amulet.*` (Amulet of Yendor), `items.artifacts.*.desc` (cada artefato tem lore própria), `items.food.pasty.amulet_*` (item de easter egg).

**Descrições de criaturas/chefes com lore:**
- `core/src/main/assets/messages/actors/actors.properties` — `actors.mobs.yogdzewa.*` e `actors.mobs.yogfist$*.*` (o deus final e avatares), `actors.mobs.dm300.*`, `actors.mobs.golem.*`, `actors.mobs.pylon.*` (máquinas de guerra anãs).

**Textos de cena:**
- `core/src/main/assets/messages/scenes/scenes.properties` — `scenes.amuletscene.text` (cena de vitória/final do jogo).

**Código que estrutura e exibe a narrativa** (não é texto em si, mas organiza onde/como ela aparece — relevante para quem for expandir o sistema, não para quem só troca texto):
- `core/src/main/java/cloud/sandino/judgementofthecangaceiro/windows/WndStory.java`, `WndJournal.java`, `WndJournalItem.java`, `WndQuest.java`, `WndBadge.java`
- `core/src/main/java/cloud/sandino/judgementofthecangaceiro/journal/Document.java`, `Journal.java`, `Notes.java`, `Bestiary.java`, `Catalog.java`
- `core/src/main/java/cloud/sandino/judgementofthecangaceiro/items/journal/{DocumentPage,GuidePage,AlchemyPage,RegionLorePage,Guidebook}.java`
- `core/src/main/java/cloud/sandino/judgementofthecangaceiro/scenes/JournalScene.java`

---

## Parte 2 — Direção temática pretendida (ainda não implementada)

Fonte: preâmbulo do `README.md` e o agente `.claude/agents/narrative-designer.md`.

- Ambientação: sertão nordestino brasileiro, início do século XX, universo do **cangaço**.
- Referências culturais centrais: Lampião, Maria Bonita, jagunços, coronelismo (mandonismo político rural), seca, religiosidade popular, literatura de cordel.
- Nome canônico do jogo: **Judgement of the Cangaceiro** (com "e" — nunca "Judgment"). Nome curto: **Cangaceiro**.
- Alerta explícito do próprio agente de narrativa: o cangaço envolveu violência real, pobreza e repressão histórica — não deve ser tratado como estética genérica de "bandido do interior" nem tom piadista sem alertar sobre o risco antes.
- Ainda não foi decidido (nem perguntado ao usuário) o tom de execução: fantasia inspirada no cangaço vs. retrato histórico mais sério vs. tom de cordel (popular, hiperbólico, oral). Isso muda completamente como cada peça da Parte 1 seria adaptada.

### 2.1 Ideias de correspondência temática (rascunho, não aprovado)

Este é um ponto de partida especulativo para discussão — **nenhum destes nomes foi decidido ou aprovado**, é só para ilustrar o tipo de tradução temática que a Parte 3 deveria formalizar quando o trabalho começar:

| Elemento original (SPD) | Papel na história | Direção temática possível a discutir |
|---|---|---|
| Amulet of Yendor | MacGuffin central, fonte de poder corruptora | Um objeto de poder ligado à seca/milagre popular? Precisa de pesquisa antes de nomear. |
| Yog-Dzewa | Deus cósmico devorador de mundos | Entidade ligada a seca/fome/"a peste" do sertão? Tema sensível — cuidado para não trivializar sofrimento histórico real. |
| Rodney / "Novo Rei" | Usurpador corrompido pelo poder | Um coronel ou "rei do sertão" corrompido pelo poder — arquétipo já existe no cangaço histórico (coronelismo). |
| Reino/corte anã | Civilização subterrânea em colapso | Um vilarejo/fazenda isolada? Precisa decidir escala. |
| Sewers/Prison/Caves/City/Halls | As 5 regiões da masmorra | Precisa de 5 biomas do sertão coerentes (ex.: caatinga, grota, açude seco, vila abandonada?) — decisão de Game Designer + Narrative Designer em conjunto. |

**Antes de nomear qualquer um destes em volume**, seguir o fluxo já descrito em `.claude/agents/narrative-designer.md`: confirmar o tom desejado com o usuário, pesquisar (WebSearch) termos históricos/culturais específicos, e editar apenas os arquivos `.properties` base (sem sufixo de idioma).

---

## Parte 3 — Glossário canônico (a preencher)

Ainda vazio — nenhum termo foi retematizado. Quando a retematização começar, preencher esta tabela para manter consistência entre `items/`, `actors/`, `levels/`, `journal/` etc., evitando que a mesma criatura/item/conceito receba nomes diferentes em partes diferentes do jogo.

| Termo original | Chave(s) `.properties` | Termo cangaceiro (canon) | Notas |
|---|---|---|---|
| _(vazio — preencher conforme o trabalho avança)_ | | | |

---

## Próximos passos sugeridos

1. Decidir o tom (fantasia inspirada / histórico-realista / cordel) com o usuário antes de qualquer renomeação em volume.
2. Priorizar o que retematizar primeiro (ex.: começar pelas 5 regiões e pelo artefato central, já que estruturam tudo o resto).
3. Preencher a Parte 3 conforme cada categoria for trabalhada, para manter consistência entre os arquivos `.properties` listados na seção 1.7.
4. Lembrar sempre: editar só os arquivos base (sem sufixo de idioma); as traduções `_xx.properties` ficam desatualizadas até sincronização via Transifex.
