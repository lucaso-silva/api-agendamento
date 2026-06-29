# API de Agendamento

API REST desenvolvida para gerenciamento de agendamentos de consultas em uma clínica médica.

O projeto foi desenvolvido com foco em boas práticas de desenvolvimento, regras de negócio, organização arquitetural e qualidade de código.

---
    
## Tecnologias Utilizadas

* Java 21
* Spring Boot 4
* Spring Data JPA
* Spring Validation
* PostgreSQL
* Flyway
* SpringDoc OpenAPI (Swagger)
* JUnit 5
* Mockito
* Maven
* Lombok

---

## Funcionalidades

### Pacientes

* Cadastrar paciente
* Listar pacientes cadastrados

### Agendamentos

* Criar agendamento
* Listar agendamentos
* Filtrar agendamentos por:

    * paciente
    * profissional
    * status
* Cancelar agendamento registrando o motivo

---

## Regras de Negócio Implementadas

* Um profissional não pode possuir dois agendamentos no mesmo horário.
* Não é permitido criar agendamentos em datas passadas.
* O cancelamento exige um motivo.
* Ao cancelar um agendamento, o registro permanece armazenado no banco de dados.
* Agendamentos cancelados não bloqueiam novos horários para o profissional.
* Apenas pacientes e profissionais existentes podem ser utilizados no agendamento.
* Não é permitido cadastro de pacientes com mesmo número de carteirinha. 

---

## Arquitetura

O projeto foi estruturado utilizando princípios inspirados em **Clean Architecture**, buscando baixo acoplamento e separação clara de responsabilidades.

```text
src/main/java
│
├── core
│   ├── domain
│   ├── dto
│   ├── exception
│   ├── gateway
│   └── usecase
│
└── infra
    └── adapter
        ├── inbound
        │   └── rest
        │       ├── api
        │       └── controller
        │
        └── outbound
            └── persistence
                ├── entity
                ├── mapper
                └── repository
```

### Principais conceitos adotados

* Casos de uso separados por responsabilidade.
* DTOs para comunicação entre camadas.
* Gateways para abstração de persistência.
* Mapeamento entre domínio e persistência.
* Tratamento global de exceções utilizando `@RestControllerAdvice`.
* Validações centralizadas nas camadas de domínio e aplicação.

---

## Como Executar o Projeto

### Pré-requisitos

* Java 21+
* Maven 3.9+
* PostgreSQL

---

### 1 - Clonar o repositório

```bash
git clone https://github.com/seu-usuario/agendamento.git
```

```bash
cd agendamento
```

---

### 2 - Criar banco de dados

Criar um banco PostgreSQL chamado:

```sql
CREATE DATABASE db_agendamentos;
```

---

### 3 - Configurar variáveis de ambiente

A aplicação utiliza as seguintes variáveis:

```bash
PG_DB_USER=seu_usuario
PG_DB_PASSWORD=sua_senha
```

Exemplo Linux/Mac:

```bash
export PG_DB_USER=postgres
export PG_DB_PASSWORD=postgres
```

Exemplo Windows (PowerShell):

```powershell
$env:PG_DB_USER="postgres"
$env:PG_DB_PASSWORD="postgres"
```

---

### 4 - Executar aplicação

Utilizando Maven:

```bash
./mvnw spring-boot:run
```

Ou:

```bash
mvn spring-boot:run
```

---

## Banco de Dados

O versionamento do banco é realizado utilizando **Flyway**.

As migrations encontram-se em:

```text
src/main/resources/db/migration
```

Ao iniciar a aplicação, as tabelas são criadas automaticamente e registros iniciais são inseridos.

---

## Documentação da API

Após iniciar a aplicação, a documentação Swagger poderá ser acessada em:

```text
http://localhost:8080/swagger-ui.html
```

ou

```text
http://localhost:8080/swagger-ui/index.html
```

---

## Endpoints

### Pacientes

| Método | Endpoint         | Descrição          |
| ------ | ---------------- | ------------------ |
| POST   | `/api/pacientes` | Cadastrar paciente |
| GET    | `/api/pacientes` | Listar pacientes   |

---

### Agendamentos

| Método | Endpoint                              | Descrição            |
| ------ | ------------------------------------- | -------------------- |
| POST   | `/api/agendamentos`                   | Criar agendamento    |
| GET    | `/api/agendamentos`                   | Listar agendamentos  |
| PATCH  | `/api/agendamentos/{id}/cancelamento` | Cancelar agendamento |

---

## Exemplos de Requisição

### Cadastrar Paciente

```http
POST /api/pacientes
```

```json
{
  "nome": "Ana Santos",
  "telefone": "81999998888",
  "idCarteirinha": "123456789"
}
```

---

### Criar Agendamento

```http
POST /api/agendamentos
```

```json
{
  "pacienteId": 1,
  "profissionalId": 1,
  "dataConsulta": "2026-07-20T09:00:00",
  "tipoAtendimento": "INICIAL"
}
```

---

### Cancelar Agendamento

```http
PATCH /api/agendamentos/1/cancelamento
```

```json
{
  "motivo": "Paciente solicitou cancelamento."
}
```

---

### Filtrar Agendamentos

```http
GET /api/agendamentos?paciente=João
```

```http
GET /api/agendamentos?profissional=Pedro
```

```http
GET /api/agendamentos?status=AGENDADO
```

---

## Tratamento de Erros

A API possui tratamento global de exceções utilizando `ProblemDetail`, retornando respostas padronizadas para:

* erros de validação;
* regras de negócio;
* recursos não encontrados;
* exceções de domínio.

Exemplo:

```json
{
  "detail": "Profissional ja possui agendamento no mesmo horário",
  "instance": "/api/agendamentos",
  "status": 409,
  "title": "Regra de negócio violada",
  "code": "agendamento.conflito-agenda",
  "timestamp": "2026-06-29T00:15:58.301170600Z"
}
```

---

## Testes

Foram desenvolvidos testes unitários utilizando:

* JUnit 5
* Mockito

Os testes cobrem principalmente as regras de negócio da aplicação de agendamentos.

Para executar:

```bash
mvn test
```

---

## Possíveis Melhorias Futuras

* Implementar autenticação e autorização.
* Adicionar testes de integração.
* Adicionar paginação nas consultas.
* Implementar cadastro e gerenciamento de profissionais.

---

## Autor

**[Lucas Oliveira da Silva](https://www.linkedin.com/in/lucas-oliveira10/)**

