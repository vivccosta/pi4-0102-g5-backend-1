# CT18: coleOemissores

| Linha | Valor |
| :--- | :--- |
| Fronteira | Banco |
| Quem chama | Backend 1, que é o único módulo que lê e grava nesta coleção |
| Quem responde | Banco |
| Atende | E1, E5 |
| Como chamar | Coleção emissores, chave emissorId |

Entrada e saída: os campos gravados são os mesmos campos lidos.

| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :--- | :--- |
| emissorId | inteiro | sim | 102 |
| nome | texto | sim | Escola de Profissões |
| cnpj | texto | sim | 12345678000199 |
| email | texto | sim | contato@escola.com.br |
| senha | texto | sim | $2a$12$e8k... |
| status | um de: pendente, ativo | sim | ativo |
| perfil | texto | não | EMISSOR |

Erros:

| Quando | O que volta |
| :--- | :--- |
| Falha de conexão com o MongoDB | O banco recusa a gravação/leitura |
| O emissorId não existe | O banco retorna vazio |
