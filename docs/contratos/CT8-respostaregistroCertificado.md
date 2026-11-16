# CT8: respostaRegistroCertificado

| Linha | Valor |
| :--- | :--- |
| Fronteira | HTTP |
| Quem chama | Backend 1 (API) |
| Quem responde | Frontend |
| Atende | E2 |
| Como chamar | Resposta HTTP do endpoint POST /api/certificados/registrar |

Entrada:

| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :--- | :--- |
| id | inteiro | sim | 501 |
| hash | texto | sim | 0x7f83b1657ff1fc53b92dc18148a1d65dfc2d4b1fa3d677284addd200126d9069 |

Saída:

| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :--- | :--- |
| sucesso | booleano | sim | true |
| mensagem | texto | sim | Registro realizado com sucesso! Hash: 0x7f83b1657ff1fc53b92dc18148a1d65dfc2d4b1fa3d677284addd200126d9069 |
| hash | texto | sim | 0x7f83b1657ff1fc53b92dc18148a1d65dfc2d4b1fa3d677284addd200126d9069 |

Erros:

| Quando | O que volta |
| :--- | :--- |
| Falha na persistência ou no fluxo geral | HTTP 500 com mensagem de erro |
