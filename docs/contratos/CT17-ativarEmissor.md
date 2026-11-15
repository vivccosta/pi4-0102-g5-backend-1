# CT17: ativarEmissor

| Linha | Valor |
| :--- | :--- |
| Fronteira | HTTP |
| Quem chama | Frontend |
| Quem responde | Backend 1 |
| Atende | E5 |
| Como chamar | PATCH /api/emissores/{emissorId}/status |

Entrada:

| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :--- | :--- |
| emissorId | inteiro | sim | 102 |
| status | um de: pendente, ativo, bloqueado | sim | ativo |

Saída:

| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :--- | :--- |
| sucesso | booleano | sim | true |
| mensagem | texto | sim | Emissor ativado com sucesso |

Erros:

| Quando | O que volta |
| :--- | :--- |
| O emissorId não existe no sistema | HTTP 404 Not Found (Emissor não encontrado) |
| O administrador não está autenticado | HTTP 401 Unauthorized |
