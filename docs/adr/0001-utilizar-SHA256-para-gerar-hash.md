# ADR 0001: Utilizar SHA-256 para geração do hash dos certificados
**Status:** aceito
**Contexto:** A API é responsável por receber os dados do certificado (como nome completo do aluno e CPF/RG) fornecidos pelo emissor e produzir a chave de autenticação que será registrada na blockchain. Precisamos garantir integridade matemática, unicidade e compatibilidade com o serviço de blockchain, sem expor dados sensíveis do aluno diretamente no bloco público. O método de hashing precisa ser determinístico, amplamente suportado e computacionalmente leve para a API.
**Decisão:** Decidiu-se utilizar o algoritmo SHA-256 aplicado sobre a concatenação normalizada dos dados do certificado e um identificador único para gerar a chave/hash de autenticação.
**Alternativas consideradas:**
- MD5 / SHA-1: Descartados devido a vulnerabilidades conhecidas de colisão criptográfica e falta de conformidade com boas práticas de segurança.
- Delegar a geração de hash diretamente para a blockchain: Descartado para manter o desacoplamento de responsabilidades, permitindo que a API valide os dados e registre no banco relacional antes de enviar a transação para o nó blockchain.
**Consequências:**
- Positivas: Garante integridade e unicidade da chave criptográfica com alta performance de execução na API, além de total compatibilidade com os padrões de hashing da blockchain.
- Negativas: Qualquer alteração mínima na formatação dos campos de entrada sem padronização prévia altera completamente o hash resultante, exigindo tratamento rigoroso de sanitização dos dados antes do cálculo.