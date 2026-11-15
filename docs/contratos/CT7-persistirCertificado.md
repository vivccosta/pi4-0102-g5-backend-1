# CT7: persistirCertificado

| Linha | Valor |
| :--- | :--- |
| Fronteira | Banco |
| Quem chama | Backend 1 (API) |
| Quem responde | Banco de Dados |
| Atende | E2 |
| Como chamar | Comando SQL INSERT INTO certificados (...) |

Entrada:

| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :--- | :--- |
| emissorId | inteiro | sim | 102 |
| nomeAluno | texto | sim | Maria Silva Santos |
| cpfAluno | Inteiro | sim | 123.456.789-10 |
| curso | texto | sim | Engenharia de Software |
| cargaHoraria | inteiro | sim | 120 |
| dataConclusao | texto | sim | 2026-06-30 |
| hash | texto | sim | 0x7f83b1657ff1fc53b92dc18148a1d65dfc2d4b1fa3d677284addd200126d9069 |
| status | texto | sim | valido |
| dataEmissao | texto | sim | 2026-06-30 |

Saída:

| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :--- | :--- |
| id | inteiro | sim | 501 |
| linhasAfetadas | inteiro | sim | 1 |

Erros:

| Quando | O que volta |
| :--- | :--- |
| Violação de chave única (hash já existe) | Unique Constraint Violation |
| Falha de comunicação | Connection Refused / Timeout |
