# CT4: registrarCertificado

## Informações Gerais
| Linha | Valor |
| :--- | :--- |
| **Fronteira** | HTTP |
| **Quem chama** | Frontend |
| **Quem responde** | Backend 1 |
| **Atende** | E2 |
| **Como chamar** | `POST /api/certificados/registrar` (JSON via HTTP) |

## Entrada
| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :---: | :--- |
| `nomeAluno` | texto | sim | Maria Silva Santos |
| `curso` | texto | sim | Engenharia de Software |
| `cargaHoraria` | inteiro | sim | 120 |
| `dataConclusao` | texto | sim | 2026-06-30 |
| `tokenEmissor` | texto | sim | eyJhbGciOiJIUzI1Ni... |

## Saída
| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :---: | :--- |
| `sucesso` | booleano | sim | true |
| `mensagem` | texto | sim | Registro realizado com sucesso! |
| `hash` | texto | sim | 0x7f83b1657ff1fc53b92dc18148a1d65dfc2d4b1fa3d677284addd200126d9069 |

## Erros mapeados
| Quando | O que volta |
| :--- | :--- |
| Emissor não autenticado | HTTP 401 Unauthorized / Token inválido |
| Campos obrigatórios ausentes | HTTP 400 Bad Request com validação dos campos |
| Falha interna ao processar | HTTP 500 Internal Server Error |