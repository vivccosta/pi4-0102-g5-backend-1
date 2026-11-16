# CT12: consultarCertificadoBanco

| Linha | Valor |
| :--- | :--- |
| Fronteira | Banco |
| Quem chama | Backend 1 (API) |
| Quem responde | Banco de Dados |
| Atende | E3 |
| Como chamar | Comando SQL SELECT * FROM certificados WHERE hash = ? |

Entrada:

| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :--- | :--- |
| hash | texto | sim | 0x7f83b1657ff1fc53b92dc18148a1d65dfc2d4b1fa3d677284addd200126d9069 |

Saída:

| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :--- | :--- |
| status | texto | sim | valido |

Erros:

| Quando | O que volta |
| :--- | :--- |
| Chave não cadastrada no banco | Registro vazio (NULL / 0 linhas retornadas) |
| Falha de conexão com a base | Connection Refused / Timeout |
