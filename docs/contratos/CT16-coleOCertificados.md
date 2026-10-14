# CT16: Coleção certificados

## Informações Gerais
| Linha | Valor |
| :--- | :--- |
| **Fronteira** | Banco |
| **Quem chama** | Backend 1 (único módulo que lê e grava nesta coleção) |
| **Quem responde** | Banco de Dados (MongoDB) |
| **Atende** | E2, E4, E6 |
| **Como chamar** | Operações na coleção `certificados`, chave `certificadoId` |

## Entrada e Saída
*(Os campos gravados são os mesmos lidos nas consultas)*

| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :---: | :--- |
| `certificadoId` | inteiro | sim | 405 |
| `emissorId` | inteiro | sim | 102 |
| `nomeAluno` | texto | sim | João Pedro Alves |
| `curso` | texto | sim | Excel Avançado |
| `cargaHoraria` | inteiro | sim | 40 |
| `dataConclusao` | data | sim | 2026-10-10 |
| `hash` | texto | sim | 0x7f83b1657ff1... |
| `status` | texto | sim | valido (enum: valido, invalido) |
| `dataEmissao` | data e hora | sim | 2026-10-14T09:30:00 |

## Erros mapeados
| Quando | O que volta |
| :--- | :--- |
| Falha de conexão com o MongoDB | O banco recusa a gravação/leitura |
| O certificadoId não existe | O banco retorna vazio |