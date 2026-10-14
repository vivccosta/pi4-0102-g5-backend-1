# CT15: listarCertificados

## Informações Gerais
| Linha | Valor |
| :--- | :--- |
| **Fronteira** | HTTP |
| **Quem chama** | Frontend |
| **Quem responde** | Backend 1 |
| **Atende** | E4 |
| **Como chamar** | `GET /api/emissores/{emissorId}/certificados` |

## Entrada
| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :---: | :--- |
| `emissorId` | inteiro | sim | 102 |

## Saída
| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :---: | :--- |
| `certificados` | lista (objeto) | sim | [ { certificadoId: 405... } ] |

**Estrutura do Objeto Certificado:**
| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :---: | :--- |
| `certificadoId` | inteiro | sim | 405 |
| `nomeAluno` | texto | sim | João Pedro Alves |
| `hash` | texto | sim | 0x7f83b1657ff1... |
| `dataEmissao` | data e hora | sim | 2026-10-14T09:30:00 |
| `status` | texto | sim | valido |

## Erros mapeados
| Quando | O que volta |
| :--- | :--- |
| O emissorId não existe | HTTP 404 Not Found (Emissor não encontrado) |