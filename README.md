# sevenfood-production-api

# Sevenfood Product API

Essa API serve para receber os pedidos da cozinha, para ser produzidos. Esse pedidos são recebidos por uma fila SQS para gerar o processamento e devolve o Status em outra Fila SQS.

## Stack Utilizada

- Java 17
- Spring Boot 3
- Flyway
- IntelliJ IDEA
- PostgreSQL 12 (PGAdmin)
- Docker & Docker Compose
- Nginx como reverse proxy
- Swagger (OpenAPI)
- JUnit 5
- Mockito
- Maven
- Kubernetes (EKS)

## Funcionalidades

1. **Produção**: recebimento de dados do pedido da API de Order via SQS.
2. **Controle de Status**: Gerenciamento do status do pedido via outra fila SQS.

## Utilização do SQS

Foi utilizado o SQS para mandar os pedidos para a API de produção (cozinha). A infraestrutura está contida no repositório: [infra-sqs-producao](https://github.com/fiapg70/infra-sqs-producao).

## Utilização do PostgreSQL

Foi utilizado o PostgreSQL para armazenar os dados. A infraestrutura está contida no repositório: [infra-rds-postgresql](https://github.com/fiapg70/infra-rds-postgresql).

## Como Rodar a API

### Pré-requisitos

- Java 17
- Docker & Docker Compose
- Maven
- PostgreSQL 12

### Passo a Passo

1. **Clone o repositório:**

   ```bash
   git clone https://github.com/fiapg70/sevenfood-production-api.git
   cd sevenfood-order-api

### Banco de Dados:
Para rodar o Banco local só rodar o docker compose da pasta mongo, com o comando 
```bash
  docker-compose up -d

### Antes de Rodar a Aplicação:
As variaves necessárias para rodar a aplicação estão na pasta env

### Rodando a Aplicação:
```bash
  mvn clean install
  mvn spring-boot:run

### Acesso à Documentação:
  Acesse a documentação da API via Swagger em http://localhost:9998/swagger-ui.html.
