# CT12: consultarCertificadoBanco

## Informações Gerais
| Linha | Valor |
| :--- | :--- |
| **Fronteira** | Banco |
| **Quem chama** | Backend 1 (API) |
| **Quem responde** | Banco de Dados |
| **Atende** | E3 |
| **Como chamar** | Comando de busca na coleção de certificados filtrando por hash |

## Entrada
| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :---: | :--- |
| `hash` | texto | sim | 0x7f83b1657ff1fc53b92dc18148a1d65dfc2d4b1fa3d677284addd200126d9069 |

## Saída
| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :---: | :--- |
| `id` | inteiro | sim | 501 |
| `nomeAluno` | texto | sim | Maria Silva Santos |
| `curso` | texto | sim | Engenharia de Software |
| `cargaHoraria` | inteiro | sim | 120 |
| `dataConclusao` | texto | sim | 2026-06-30 |
| `hash` | texto | sim | 0x7f83b1657ff1fc... |

## Erros mapeados
| Quando | O que volta |
| :--- | :--- |
| Chave não cadastrada no banco | Registro vazio (NULL / 0 linhas retornadas) |
| Falha de conexão com a base | Connection Refused / Timeout |