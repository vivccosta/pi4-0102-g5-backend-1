# CT13: retornoConsultaBanco

| Linha | Valor |
| :--- | :--- |
| Fronteira | Banco |
| Quem chama | Banco de Dados |
| Quem responde | Backend 1 (API) |
| Atende | E3 |
| Como chamar | Retorno da query SQL executada |

Entrada:

| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :--- | :--- |
| resultadoQuery | cursor / lista | sim | ResultSet com dados ou vazio |

Saída:

| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :--- | :--- |
| registrosEncontrados | inteiro | sim | 1 |
| dadosCertificado | objeto | não | { "id": 501, "nomeAluno": "Maria Silva Santos", ... } |

Erros:

| Quando | O que volta |
| :--- | :--- |
| Queda de conexão durante leitura | SQLException de conexão interrompida |
