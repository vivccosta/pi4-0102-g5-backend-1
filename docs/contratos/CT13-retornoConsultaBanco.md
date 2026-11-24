# CT13: retornoConsultaBanco

| Linha | Valor |
| :--- | :--- |
| Fronteira | Banco |
| Quem chama | Banco de Dados |
| Quem responde | Backend 1 (API) |
| Atende | E3 |
| Como chamar | Retorno da consulta na coleção certificados do MongoDB |

Entrada:

| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :--- | :--- |
| resultadoConsulta | objeto / Optional | sim | Documento Certificado ou Optional.empty |

Saída:

| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :--- | :--- |
| encontrado | booleano | sim | true |
| dadosCertificado | objeto | não | { "certificadoId": 501, "nomeAluno": "Maria Silva Santos", "curso": "Engenharia de Software", "status": "VALIDO" } |

Erros:

| Quando | O que volta |
| :--- | :--- |
| Queda de conexão durante leitura | Exceção de conexão com o MongoDB interrompida |