# CT11: retornoConsultaBlockchain

## Informações Gerais
| Linha | Valor |
| :--- | :--- |
| **Fronteira** | WebSocket |
| **Quem chama** | Backend 2 (Blockchain) |
| **Quem responde** | Backend 1 (API) |
| **Atende** | E3 |
| **Como chamar** | Mensagem recebida via WebSocket no evento `retornoConsultaBlockchain` (JSON via WS) |

## Entrada
| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :---: | :--- |
| `hash` | texto | sim | 0x7f83b1657ff1fc53b92dc18148a1d65dfc2d4b1fa3d677284addd200126d9069 |

## Saída
| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :---: | :--- |
| `statusConsulta` | booleano | sim | true |
| `encontrado` | booleano | sim | true |
| `bloco` | inteiro | não | 15423891 |

## Erros mapeados
| Quando | O que volta |
| :--- | :--- |
| Erro de leitura no nó da Blockchain | Mensagem via WS com `statusConsulta: false` e detalhes do erro |