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
| O emissorId não existe | HTTP 404 Not Found (Emissor não encontrado) |
