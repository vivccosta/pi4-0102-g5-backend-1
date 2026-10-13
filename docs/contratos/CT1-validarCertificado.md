# Contrato CT1: validarCertificado

| Linha | Valor |
| :--- | :--- |
| **Fronteira** | WebSocket |
| **Quem chama** | Frontend |
| **Quem responde** | Backend 1 (API) |
| **Atende** | E7 |
| **Como chamar** | Canal `/certificados`, mensagem do tipo `validarCertificado` |

## Entrada
| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :--- | :--- |
| chaveAutenticacao | texto | não | `CT-2026-8A71C2` |
| arquivoPdf | texto | não | `JVBERi0xLjcK...` (base64 do PDF) |

## Saída
| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :--- | :--- |
| statusValidacao | um de: valido, invalido, revogado, nao_encontrado | sim | `valido` |
| chaveAutenticacao | texto | sim | `CT-2026-8A71C2` |
| arquivoComprovantePdf | texto | sim | `JVBERi0xLjcK...` (PDF em base64) |

## Erros
| Quando | O que volta |
| :--- | :--- |
| Chave ou arquivo não informados | Erro `DADOS_VALIDACAO_AUSENTES` |
| Certificado não encontrado na base | Status `nao_encontrado` com mensagem explicativa |