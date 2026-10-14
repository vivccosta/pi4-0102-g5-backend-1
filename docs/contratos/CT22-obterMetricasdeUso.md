# CT22: obterMetricasUso

## Informações Gerais
| Linha | Valor |
| :--- | :--- |
| **Fronteira** | HTTP |
| **Quem chama** | Frontend |
| **Quem responde** | Backend 1 |
| **Atende** | E8 |
| **Como chamar** | Requisição HTTP (`GET`) para consultar as métricas de uso |

## Entrada
| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :---: | :--- |
| `token` | texto | sim | eyJhbGciOi... |

## Saída
| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :---: | :--- |
| `totalEmitido` | inteiro | sim | 150 |
| `totalConsultado` | inteiro | sim | 320 |

## Erros mapeados
| Quando | O que volta |
| :--- | :--- |
| Usuário não autenticado | HTTP 401 Unauthorized |
| Usuário sem permissão | HTTP 403 Forbidden |
| Falha ao consultar os dados | HTTP 500 Internal Server Error |