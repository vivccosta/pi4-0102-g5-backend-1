# CT9: validarCertificado

| Linha | Valor |
| :--- | :--- |
| Fronteira | HTTP |
| Quem chama | Frontend |
| Quem responde | Backend 1 (API) |
| Atende | E3 |
| Como chamar | POST /api/certificados/validar (multipart/form-data via HTTP) |

Entrada:

| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :--- | :--- |
| tipoValidacao | texto | sim | CHAVE |
| chaveAutenticacao | texto | não | 0x7f83b1657ff1fc53b92dc18148a1d65dfc2d4b1fa3d677284addd200126d9069 |
| arquivoPdf | binário / arquivo | não | certificado_aluno.pdf (suporte no backend; não utilizado no frontend desta versão) |

Saída:

| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :--- | :--- |
| valido | booleano | sim | true |
| mensagem | texto | sim | Certificado válido |
| nomeAluno | texto | não | Maria Silva Santos |
| curso | texto | não | Engenharia de Software |
| dataConclusao | texto | não | 2026-06-30 |
| hash | texto | não | 0x7f83b1657ff1fc53b92dc18148a1d65dfc2d4b1fa3d677284addd200126d9069 |

Erros:

| Quando | O que volta |
| :--- | :--- |
| Nenhum parâmetro fornecido (nem chave nem PDF) | HTTP 400 Bad Request com mensagem de validação |
| Falha interna de comunicação nos serviços | HTTP 500 Internal Server Error |