# CT12: consultarCertificadoBanco

| Linha | Valor |
| :--- | :--- |
| Fronteira | Banco |
| Quem chama | Backend 1 (API) |
| Quem responde | Banco de Dados |
| Atende | E3 |
| Como chamar | Consulta na coleção certificados do MongoDB pelo campo hash (Spring Data MongoDB) |

Entrada:

| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :--- | :--- |
| hash | texto | sim | 0x7f83b1657ff1fc53b92dc18148a1d65dfc2d4b1fa3d677284addd200126d9069 |

Saída:

| Campo | Tipo | Obrigatório | Exemplo |
| :--- | :--- | :--- | :--- |
| documentoCertificado | objeto | não | Documento contendo certificadoId, emissorId, nomeAluno, curso, cargaHoraria, dataConclusao, hash, status e dataEmissao |

Erros:

| Quando | O que volta |
| :--- | :--- |
| Chave não cadastrada no banco | Retorno vazio (Optional.empty / documento não encontrado) |
| Falha de conexão com o MongoDB | Exceção de conexão / Timeout |