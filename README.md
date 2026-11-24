# Timbro

O **Timbro** é um sistema web para registrar evidências de certificados por meio de hash **SHA-256** e permitir a consulta pública pela chave de autenticação.

A arquitetura do projeto é dividida em quatro componentes principais:
- **Frontend** (Interface Web)
- **Backend 1** (API HTTP)
- **Backend 2** (Blockchain / WebSocket)
- **MongoDB** (Banco de Dados)

---

## Integrantes

| Integrante | RA | Responsabilidade |
| :--- | :---: | :--- |
| Caio Lucena Andrade | `25011275` | Backend 2 / Blockchain |
| Emely Miguel Moreira | `25896580` | Backend 1 / API |
| Gabriel Biazon Galeazzi | `25004250` | Banco de dados e documentação de contratos/decisões |
| Viviane Conceição Costa | `25897398` | Frontend, testes e telas |

---

## Repositórios

- **Frontend:** [vivccosta/pi4-0102-g5-frontend](https://github.com/vivccosta/pi4-0102-g5-frontend)
- **Backend 1** *(repositório principal; contém o `docker-compose.yml`)*: [vivccosta/pi4-0102-g5-backend-1](https://github.com/vivccosta/pi4-0102-g5-backend-1)
- **Backend 2:** [vivccosta/pi4-0102-g5-backend-2](https://github.com/vivccosta/pi4-0102-g5-backend-2)

---

## Pré-requisitos

### Para execução com Docker
- **Git** para clonar os repositórios.
- **Docker Engine** ou **Docker Desktop** com suporte ao comando `docker compose` (Compose V2).

### Para execução de testes fora dos contêineres
- **Frontend:** Node.js `>= 24` e npm `>= 10`
- **Backend 1:** Java `21`
- **Backend 2:** Python `3.12` e a dependência `websockets` (listada no `requirements.txt`)

---

## Instalação e Execução

### 1. Clonar os três repositórios

Os caminhos de build do Docker Compose pressupõem que os três diretórios estejam **lado a lado** e com os **nomes originais** dos repositórios:

```bash
mkdir timbro
cd timbro

git clone https://github.com/vivccosta/pi4-0102-g5-backend-1.git
git clone https://github.com/vivccosta/pi4-0102-g5-backend-2.git
git clone https://github.com/vivccosta/pi4-0102-g5-frontend.git

cd pi4-0102-g5-backend-1
```

### 2. Configurar variáveis de ambiente e subir os serviços

O arquivo de exemplo versionado chama-se `.env.exemple`. Como o Docker Compose lê automaticamente um arquivo chamado `.env` na pasta do Compose, é necessário copiá-lo antes de iniciar os contêineres:

```bash
cp .env.exemple .env
docker compose up --build
```

---

## Endereços e Portas

| Serviço | Endereço / Porta | Uso |
| :--- | :--- | :--- |
| **Frontend** | `http://localhost:3000` | Interface web servida pelo Nginx. |
| **Backend 1** | `http://localhost:8080` | API HTTP chamada pelo navegador. |
| **MongoDB** | `localhost:27017` | Porta publicada pelo Compose; normalmente acessada pela aplicação dentro da rede Docker. |
| **Backend 2** | `localhost:8000` (publicada)<br>`backend2:8000` (uso interno pelo Backend 1) | Servidor WebSocket; endpoint interno `ws://backend2:8000/ws/blockchain`. |

> **Nota sobre comunicação:** O frontend gera o arquivo `config.js` em tempo de execução apontando `REACT_APP_API_URL=http://localhost:8080`. Toda a comunicação entre o navegador e o Backend 1 ocorre via HTTP/JSON na porta `8080`, enquanto o canal WebSocket permanece restrito à comunicação interna entre o Backend 1 e o Backend 2 em `ws://backend2:8000/ws/blockchain`. **Não** se deve configurar o navegador com os hostnames internos da rede Docker (`backend1` ou `backend2`).

---

## Encerrar os Serviços

Para parar e remover os contêineres **preservando** o volume de dados do MongoDB:

```bash
docker compose down
```

Para parar os contêineres e **remover também o volume de dados** do MongoDB:

```bash
# ATENÇÃO: esta ação apaga os dados persistidos no MongoDB
docker compose down -v
```

---

## Como Usar

> **Conta de administrador:** Para gerar o administrador do sistema, execute o script bash chamado `user_tests`.

1. **Solicitação do emissor:** Abra a aplicação (`http://localhost:3000`), acesse a tela de cadastro e envie nome, e-mail, CNPJ e senha. O cadastro ficará com status pendente.
2. **Aprovação:** Um administrador autenticado acessa a lista de emissores pendentes e altera o status do cadastro para `ATIVO`.
3. **Login do emissor:** Faça login utilizando o e-mail e a senha de um emissor aprovado.
4. **Registro de certificado:** Preencha os campos do certificado e realize o registro. A chave retornada deve ser copiada e entregue ao titular/solicitante por um canal externo.
5. **Consulta pública:** Acesse a rota `/verificar` e cole a chave de autenticação. *(A versão atual do frontend não oferece upload de PDF.)*
6. **Histórico e revogação:** No painel do emissor, consulte os registros realizados e acione **Revogar** quando necessário.

---

## Executar os Testes por Módulo

### Frontend

```bash
cd ../pi4-0102-g5-frontend
npm ci
npm test
```

### Backend 1

```bash
cd ../pi4-0102-g5-backend-1
./mvnw test
```

### Backend 2

```bash
cd ../pi4-0102-g5-backend-2
python3 -m venv .venv

# Ativar ambiente virtual:
# Linux/macOS:
source .venv/bin/activate
# Windows PowerShell:
# .venv\Scripts\Activate.ps1

pip install -r requirements.txt
python tests/test_blockchain.py
python tests/test_servidor.py
```