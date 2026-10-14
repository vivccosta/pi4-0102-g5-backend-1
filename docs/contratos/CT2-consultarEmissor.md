# CT2: consultarEmissor

## Informações Gerais
| Linha | Valor |
| :--- | :--- |
| **Fronteira** | Banco |
| **Quem chama** | Backend 1 |
| **Quem responde** | Banco de Dados |
| **Atende** | E1 |
| **Como chamar** | Consulta SQL / Query no banco |

## Entrada
| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :---: | :--- |
| `email` | texto | sim | emissor@universidade.edu.br |

## Saída
| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :---: | :--- |
| `id` | inteiro | sim | 102 |
| `nome` | texto | sim | Carlos Eduardo |
| `email` | texto | sim | emissor@universidade.edu.br |
| `senha` | texto | sim | $2a$12$e8k... |
| `perfil` | texto | sim | EMISSOR |

## Erros mapeados
| Quando | O que volta |
| :--- | :--- |
| Registro não encontrado no banco | Retorno vazio (NULL / 0 linhas encontradas) |
| Falha de conexão com o banco | Exceção de conexão recusada / Timeout |