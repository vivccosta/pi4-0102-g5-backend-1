# CT14: respostaValidacaoCertificado

| Linha | Valor |
| :--- | :--- |
| Fronteira | HTTP |
| Quem chama | Backend 1 (API) |
| Quem responde | Frontend |
| Atende | E3 |
| Como chamar | Resposta HTTP do endpoint POST /api/certificados/validar |

Entrada:

| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :--- | :--- |
| status | booleano | sim | true |
| dadosValidados | objeto | não | { "nomeAluno": "Maria Silva Santos", "curso": "Engenharia de Software" } |

Saída:

| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :--- | :--- |
| nomeAluno | texto | sim | Maria Silva Santos |
| cpfAluno | Inteiro | sim | 123.456.789-10 |
| curso | texto | sim | Engenharia de Software |
| cargaHoraria | inteiro | sim | 120 |
| dataConclusao | texto | sim | 2026-06-30 |
| status | texto | sim | valido |

Erros:

| Quando | O que volta |
| :--- | :--- |
| Certificado inválido / não encontrado | HTTP 200/404 com corpo { "valido": false, "mensagem": "Certificado inválido" } |
| Erro de processamento interno | HTTP 500 Internal Server Error |
