# CT2: consultarEmissor

| Linha | Valor |
| :--- | :--- |
| Fronteira | Banco |
| Quem chama | Backend 1 |
| Quem responde | Banco de Dados |
| Atende | E1 |
| Como chamar | Consulta na coleção emissores do MongoDB pelo campo email (Spring Data MongoDB) |

Entrada:

| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :--- | :--- |
| email | texto | sim | emissor@universidade.edu.br |

Saída:

| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :--- | :--- |
| emissorId | inteiro | sim | 102 |
| nome | texto | sim | Carlos Eduardo |
| email | texto | sim | emissor@universidade.edu.br |
| senha | texto | sim | $2a$10$9FBNSUyQzF1PmO6RLI1snu... |
| status | texto | sim | ATIVO |
| perfil | texto | sim | EMISSOR |

Erros:

| Quando | O que volta |
| :--- | :--- |
| Registro não encontrado no banco | Retorno vazio (Optional.empty / documento não encontrado) |
| Falha de conexão com o banco | Exceção de conexão com o MongoDB / Timeout |