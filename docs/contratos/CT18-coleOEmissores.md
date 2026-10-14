# CT18: Coleção emissores

## Informações Gerais
| Linha | Valor |
| :--- | :--- |
| **Fronteira** | Banco |
| **Quem chama** | Backend 1 (único módulo que lê e grava nesta coleção) |
| **Quem responde** | Banco de Dados (MongoDB) |
| **Atende** | E5 |
| **Como chamar** | Operações na coleção `emissores`, chave `emissorId` |

## Entrada e Saída
*(Os campos gravados são os mesmos lidos nas consultas)*

| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :---: | :--- |
| `emissorId` | inteiro | sim | 102 |
| `nome` | texto | sim | Escola de Profissões |
| `cnpjCpf` | texto | sim | 12345678000199 |
| `email` | texto | sim | contato@escola.com.br |
| `senha` | texto | sim | $2a$12$e8k... |
| `status` | texto | sim | ativo (enum: pendente, ativo, bloqueado) |

## Erros mapeados
| Quando | O que volta |
| :--- | :--- |
| Falha de conexão com o MongoDB | O banco recusa a gravação/leitura |
| O emissorId não existe | O banco retorna vazio |