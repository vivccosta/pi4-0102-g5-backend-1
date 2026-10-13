# Contrato CT3: consultarMetadados

| Linha | Valor |
| :--- | :--- |
| **Fronteira** | Banco |
| **Quem chama** | Backend 1 (API) |
| **Quem responde** | Banco de Dados (MongoDB) |
| **Atende** | E7 |
| **Como chamar** | Coleção `certificados`, chave `chaveAutenticacao` |

## Entrada e Saída
| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :--- | :--- |
| certificadoId | texto | sim | `cert_998877` |
| emissorId | texto | sim | `emp_12345` |
| nomeAluno | texto | sim | `Maria Silva Santos` |
| curso | texto | sim | `Excel Avançado` |
| cargaHoraria | inteiro | sim | `40` |
| dataEmissao | data | sim | `2026-08-15` |
| chaveAutenticacao | texto | sim | `CT-2026-8A71C2` |
| hashPdf | texto | sim | `0x7f83b16...` |
| status | um de: valido, revogado | sim | `valido` |

## Erros
| Quando | O que volta |
| :--- | :--- |
| Registro não encontrado na coleção | Retorno vazio (null) |
| Falha de conexão com o MongoDB | Exceção de timeout / falha de driver |