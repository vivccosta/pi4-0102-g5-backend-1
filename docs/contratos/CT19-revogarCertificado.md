# CT19: revogarCertificado

| Linha | Valor |
| :--- | :--- |
| Fronteira | HTTP |
| Quem chama | Frontend |
| Quem responde | Backend 1 |
| Atende | E6 |
| Como chamar | POST /api/certificados/{certificadoId}/revogar |

Entrada:

| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :--- | :--- |
| certificadoId | inteiro | sim | 405 |

Saída:

| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :--- | :--- |
| sucesso | booleano | sim | true |
| mensagem | texto | sim | Certificado revogado com sucesso |

Erros:

| Quando | O que volta |
| :--- | :--- |
| O certificadoId não existe | HTTP 404 Not Found (Certificado não encontrado) |
| O certificado já está como "invalido" | HTTP 400 Bad Request (Certificado já revogado) |
