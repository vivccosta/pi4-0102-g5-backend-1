# CT14: respostaValidacaoCertificado

## Informações Gerais
| Linha | Valor |
| :--- | :--- |
| **Fronteira** | HTTP |
| **Quem chama** | Backend 1 (API) |
| **Quem responde** | Frontend |
| **Atende** | E3 |
| **Como chamar** | Resposta HTTP do endpoint `POST /api/certificados/validar` |

## Entrada (Processada pelo Backend)
| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :---: | :--- |
| `status` | booleano | sim | true |
| `dadosValidados`| objeto | não | `{ "nomeAluno": "Maria Silva Santos", "curso": "Engenharia de Software" }` |

## Saída (Enviada ao Frontend)
| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :---: | :--- |
| `valido` | booleano | sim | true |
| `mensagem` | texto | sim | Certificado válido |
| `nomeAluno` | texto | não | Maria Silva Santos |
| `curso` | texto | não | Engenharia de Software |
| `dataConclusao` | texto | não | 2026-06-30 |
| `hash` | texto | não | 0x7f83b1657ff1fc53b92dc18148a1d65dfc2d4b1fa3d677284addd200126d9069 |

## Erros mapeados
| Quando | O que volta |
| :--- | :--- |
| Certificado inválido / não encontrado | HTTP 200/404 com corpo `{ "valido": false, "mensagem": "Certificado inválido" }` |
| Erro de processamento interno | HTTP 500 Internal Server Error |