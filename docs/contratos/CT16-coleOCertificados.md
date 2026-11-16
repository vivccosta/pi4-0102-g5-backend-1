# CT16: certificados

| Linha | Valor |
| :--- | :--- |
| Fronteira | Banco |
| Quem chama | Backend 1, que é o único módulo que lê e grava nesta coleção |
| Quem responde | Banco |
| Atende | E2, E4, E6 |
| Como chamar | Coleção certificados, chave certificadoId |

Entrada e saída: os campos gravados são os mesmos campos lidos.

| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :--- | :--- |
| nomeAluno | texto | sim | Maria Silva Santos |
| cpfAluno | Inteiro | sim | 123.456.789-10 |
| curso | texto | sim | Engenharia de Software |
| cargaHoraria | inteiro | sim | 120 |
| dataConclusao | texto | sim | 2026-06-30 |
| hash | texto | sim | 0x7f83b1657ff1fc53b92dc18148a1d65dfc2d4b1fa3d677284addd200126d9069 |
| status | texto | sim | valido |
| dataEmissao | texto | sim | 2026-06-30 |

Erros:

| Quando | O que volta |
| :--- | :--- |
| Falha de conexão com o MongoDB | O banco recusa a gravação/leitura |
| O certificadoId não existe | O banco retorna vazio |
