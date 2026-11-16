# CT9: validarCertificado

| Linha | Valor |
| :--- | :--- |
| Fronteira | HTTP |
| Quem chama | Frontend |
| Quem responde | Backend 1 (API) |
| Atende | E3 |
| Como chamar | POST /api/certificados/validar (JSON / Multipart via HTTP) |

Entrada:

| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :--- | :--- |
| tipoValidacao | texto | sim | PDF |
| arquivoPdf | binário / arquivo | não | certificado_aluno.pdf |
| chaveAutenticacao | texto | não | 0x7f83b1657ff1fc53b92dc18148a1d65dfc2d4b1fa3d677284addd200126d9069 |

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
| Nenhum parâmetro fornecido (nem PDF nem chave) | HTTP 400 Bad Request com mensagem de validação |
| Falha interna de comunicação nos serviços | HTTP 500 Internal Server Error |
