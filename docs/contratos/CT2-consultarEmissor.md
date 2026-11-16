# CT2: consultarEmissor

| Linha | Valor |
| :--- | :--- |
| Fronteira | Banco |
| Quem chama | Backend 1 |
| Quem responde | Banco de Dados |
| Atende | E1 |
| Como chamar | Consulta SQL / Query no banco |

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
| perfil | texto | sim | EMISSOR |

Erros:

| Quando | O que volta |
| :--- | :--- |
| Registro não encontrado no banco | Retorno vazio (NULL / 0 linhas encontradas) |
| Falha de conexão com o banco | Exceção de conexão recusada / Timeout |
