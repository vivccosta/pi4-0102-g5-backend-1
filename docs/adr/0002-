# ADR 0002: Armazenar apenas metadaos e hash do certificado no banco de dados
**Status:** aceito
**Contexto:** O sistema precisa viabilizar a consulta pública rápida da veracidade do certificado e manter um painel de histórico para o emissor gerenciar suas emissões e revogações. Armazenar arquivos brutos diretamente no banco da API ou salvar dados relacionais dentro da blockchain traria custos elevados de armazenamento e perda de performance. A API precisa atuar como a camada intermediária eficiente entre a interface, o banco de dados e a rede blockchain.
**Decisão:** Decidiu-se armazenar no banco de dados da API apenas os metadados dos certificados (identificação do aluno, id do emissor, data, status) vinculados ao respectivo hash, enviando para a blockchain estritamente o hash.
**Alternativas consideradas:**
- Armazenar o documento/arquivo binário completo em base64 no banco de dados: Descartado por sobrecarregar o banco de dados com tráfego desnecessário de I/O e aumentar os custos de armazenamento.
- Consultar a blockchain em todas as listagens de histórico do painel: Descartado pelo impacto negativo na latência das requisições e pelo processamento desnecessário na rede **Consequências:**
- Positivas: Melhora expressivamente a velocidade de listagem e filtragem dos certificados no painel do emissor, simplificando consultas e reduzindo o consumo de armazenamento.
- Negativas: Exige a manutenção de consistência eventual entre o estado salvo no banco de dados relacional e a confirmação efetiva do registro na blockchain.