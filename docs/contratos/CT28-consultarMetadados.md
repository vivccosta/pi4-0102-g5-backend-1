# CT28: consultarMetadados

## Informações Gerais
| Linha | Valor |
| :--- | :--- |
| **Fronteira** | Banco |
| **Quem chama** | Backend 1 (API) |
| **Quem responde** | Banco de Dados (MongoDB) |
| **Atende** | E7 |
| **Como chamar** | Operação na coleção `certificados`, chave `chaveAutenticacao` |

## Entrada e Saída
*(Os campos lidos na devolução da consulta)*

| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :---: | :--- |
| `certificadoId` | texto | sim | cert_998877 |
| `emissorId` | texto | sim | emp_12345 |
| `nomeAluno` | texto | sim | Maria Silva Santos |
| `curso` | texto | sim | Excel Avançado |
| `cargaHoraria` | inteiro | sim | 40 |
| `dataEmissao` | data | sim | 2026-08-15 |
| `chaveAutenticacao` | texto | sim | CT-2026-8A71C2 |
| `hashPdf` | texto | sim | 0x7f83b16... |
| `status` | texto | sim | valido (enum: valido, revogado) |

## Erros mapeados
| Quando | O que volta |
| :--- | :--- |
| Registro não encontrado na coleção | Retorno vazio (null) |
| Falha de conexão com o MongoDB | Exceção de timeout / falha de driver |