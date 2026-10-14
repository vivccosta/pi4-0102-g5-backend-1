# CT3: respostaAutenticacao

## Informações Gerais
| Linha | Valor |
| :--- | :--- |
| **Fronteira** | HTTP |
| **Quem chama** | Backend 1 |
| **Quem responde** | Frontend |
| **Atende** | E1 |
| **Como chamar** | Resposta HTTP do endpoint `POST /api/auth/login` |

## Entrada (Processada pelo Backend)
| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :---: | :--- |
| `statusValidacao` | booleano | sim | false |
| `codigoErro` | inteiro | sim | 401 |

## Saída (Enviada ao Frontend)
| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :---: | :--- |
| `autenticado` | booleano | sim | false |
| `mensagem` | texto | sim | Acesso não autorizado. Apenas emissores cadastrados podem acessar esta área. |

## Erros mapeados
| Quando | O que volta |
| :--- | :--- |
| O Backend 1 não consegue processar o retorno | HTTP 500 Internal Server Error |