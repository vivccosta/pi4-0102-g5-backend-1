# CT7: persistirCertificado

## Informações Gerais
| Linha | Valor |
| :--- | :--- |
| **Fronteira** | Banco |
| **Quem chama** | Backend 1 (API) |
| **Quem responde** | Banco de Dados |
| **Atende** | E2 |
| **Como chamar** | Comando SQL / NoSQL (Inserção na coleção de certificados) |

## Entrada
| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :---: | :--- |
| `nomeAluno` | texto | sim | Maria Silva Santos |
| `curso` | texto | sim | Engenharia de Software |
| `dataConclusao` | texto | sim | 2026-06-30 |
| `cargaHoraria` | inteiro | sim | 120 |
| `hash` | texto | sim | 0x7f83b1657ff1fc53b92dc18148a1d65dfc2d4b1fa3d677284addd200126d9069 |

## Saída
| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :---: | :--- |
| `id` | inteiro | sim | 501 |
| `linhasAfetadas` | inteiro | sim | 1 |

## Erros mapeados
| Quando | O que volta |
| :--- | :--- |
| Violação de chave única (hash já existe) | Unique Constraint Violation |
| Falha de comunicação | Connection Refused / Timeout |