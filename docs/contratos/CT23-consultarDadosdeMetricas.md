# CT23: consultarDados

## Informações Gerais
| Linha | Valor |
| :--- | :--- |
| **Fronteira** | Banco |
| **Quem chama** | Backend 1 |
| **Quem responde** | Banco de Dados |
| **Atende** | E8 |
| **Como chamar** | Consulta (Query/Agregação) para obter os totais de certificados |

## Entrada
| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :---: | :--- |
| `tipoConsulta` | texto | sim | emitidos (ou consultados) |

## Saída
| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :---: | :--- |
| `total` | inteiro | sim | 150 |

## Erros mapeados
| Quando | O que volta |
| :--- | :--- |
| Falha de conexão com o banco | Erro de conexão ou timeout |
| Consulta inválida | Erro na execução da consulta / agregação |
| Nenhum registro encontrado | Total igual a zero |