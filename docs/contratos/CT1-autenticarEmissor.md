# CT1: autenticarEmissor

## Informações Gerais
| Linha | Valor |
| :--- | :--- |
| **Fronteira** | HTTP |
| **Quem chama** | Frontend |
| **Quem responde** | Backend 1 |
| **Atende** | E1 |
| **Como chamar** | `POST /api/auth/login` (JSON via HTTP) |

## Entrada
| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :---: | :--- |
| `email` | texto | sim | emissor@universidade.edu.br |
| `senha` | texto | sim | SenhaForte@123 |

## Saída
| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :---: | :--- |
| `token` | texto | sim | eyJhbGciOiJIUzI1NiIsInR5cCI6... |
| `nome` | texto | sim | Carlos Eduardo |
| `perfil` | texto | sim | EMISSOR |

## Erros mapeados
| Quando | O que volta |
| :--- | :--- |
| Credenciais inválidas / Usuário não autorizado | HTTP 401 Unauthorized com mensagem: "Acesso não autorizado. Apenas emissores cadastrados podem acessar esta área." |
| Campos obrigatórios em branco | HTTP 400 Bad Request com validação dos campos |