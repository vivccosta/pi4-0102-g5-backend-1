# CT15: listarCertificados

| Linha | Valor |
| :--- | :--- |
| Fronteira | HTTP |
| Quem chama | Frontend |
| Quem responde | Backend 1 |
| Atende | E4 |
| Como chamar | GET /api/emissores/{emissorId}/certificados |

Entrada:

| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :--- | :--- |
| emissorId | inteiro | sim | 102 |

Saída:

| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :--- | :--- |
| certificados | lista de (objeto certificado) | sim | lista de objeto certificado |

Objeto certificado:

| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :--- | :--- |
| certificadoId | inteiro | sim | 501 |
| nomeAluno | texto | sim | Maria Silva Santos |
| hash | texto | sim | 0x7f83b1657ff1fc53b92dc18148a1d65dfc2d4b1fa3d677284addd200126d9069 |
| dataEmissao | texto | sim | 2026-06-30T12:13:00 |
| status | texto | sim | VALIDO |

Erros:

| Quando | O que volta |
| :--- | :--- |
| Emissor não autenticado | HTTP 401 Unauthorized |
| emissorId da URL não corresponde ao emissor autenticado | HTTP 403 Forbidden |