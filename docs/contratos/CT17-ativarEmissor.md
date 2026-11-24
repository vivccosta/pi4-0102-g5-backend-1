# CT17: ativarEmissor

| Linha | Valor |
| :--- | :--- |
| Fronteira | HTTP |
| Quem chama | Frontend |
| Quem responde | Backend 1 |
| Atende | E5 |
| Como chamar | PUT /api/admin/emissores/{emissorId}/status |

Entrada:

| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :--- | :--- |
| emissorId | inteiro | sim | 102 |
| novoStatus | um de: PENDENTE, ATIVO | sim | ATIVO |

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