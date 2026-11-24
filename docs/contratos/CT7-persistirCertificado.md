# CT7: persistirCertificado

| Linha | Valor |
| :--- | :--- |
| Fronteira | Banco |
| Quem chama | Backend 1 (API) |
| Quem responde | Banco de Dados |
| Atende | E2 |
| Como chamar | Gravação de documento na coleção certificados do MongoDB (Spring Data MongoDB) |

Entrada:

| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :--- | :--- |
| certificadoId | inteiro | sim | 501 |
| emissorId | inteiro | sim | 102 |
| nomeAluno | texto | sim | Maria Silva Santos |
| curso | texto | sim | Engenharia de Software |
| cargaHoraria | inteiro | sim | 120 |
| dataConclusao | texto | sim | 2026-06-30 |
| hash | texto | sim | 0x7f83b1657ff1fc53b92dc18148a1d65dfc2d4b1fa3d677284addd200126d9069 |
| status | texto | sim | VALIDO |
| dataEmissao | texto | sim | 2026-06-30T12:13:00 |

Saída:

| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :--- | :--- |
| certificadoId | inteiro | sim | 501 |
| hash | texto | sim | 0x7f83b1657ff1fc53b92dc18148a1d65dfc2d4b1fa3d677284addd200126d9069 |

Erros:

| Quando | O que volta |
| :--- | :--- |
| Falha de gravação ou comunicação com o MongoDB | Exceção de persistência / Connection Refused / Timeout |