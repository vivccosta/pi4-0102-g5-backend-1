# CT9: validarCertificado

## Informações Gerais
| Linha | Valor |
| :--- | :--- |
| **Fronteira** | HTTP |
| **Quem chama** | Frontend |
| **Quem responde** | Backend 1 (API) |
| **Atende** | E3, E7 |
| **Como chamar** | `POST /api/certificados/validar` (JSON / Multipart via HTTP) |

## Entrada
| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :---: | :--- |
| `tipoValidacao` | texto | sim | PDF |
| `arquivoPdf` | binário/arquivo | não | certificado_aluno.pdf |
| `chaveAutenticacao`| texto | não | 0x7f83b1657ff1fc... |

## Saída
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
| Nenhum parâmetro fornecido (nem PDF nem chave) | HTTP 400 Bad Request com mensagem de validação |
| Falha interna de comunicação nos serviços | HTTP 500 Internal Server Error |