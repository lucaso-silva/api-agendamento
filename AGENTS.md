# Contexto do projeto

API REST para agendamento de consultas médicas, criada como projeto de estudo.

## Stack

- Java 21
- Spring Boot 4
- Spring Data JPA
- PostgreSQL
- Flyway
- Bean Validation
- SpringDoc OpenAPI
- Maven
- JUnit 5 e Mockito
- Lombok

## Arquitetura

O projeto segue uma organização inspirada em Clean Architecture:

- `core/domain`: entidades e regras de domínio;
- `core/usecase`: casos de uso e regras de aplicação;
- `core/gateway`: contratos para acesso a dados;
- `core/dto`: objetos de entrada e saída;
- `infra/adapter/inbound`: REST, controllers e tratamento de exceções;
- `infra/adapter/outbound`: persistência JPA, entidades, mapeadores e repositórios;
- `src/main/resources/db/migration`: migrations do Flyway.

Preserve a direção das dependências: `core` não deve depender de classes em `infra`.

## Objetivo de estudo

Este repositório é usado para praticar Java e Spring Boot.

Ao receber uma solicitação de mudança:

1. Explique o problema, impactos e alternativas antes de propor código.
2. Sugira uma tarefa pequena, com critérios de aceite e testes esperados.
3. Não implemente código de produção sem autorização explícita.
4. Priorize orientação, revisão e perguntas que me façam raciocinar.
5. Ao revisar código, destaque pontos positivos e melhorias objetivas.

## Qualidade e segurança

- Não exponha entidades JPA diretamente na API; use DTOs.
- Preserve e amplie as validações de negócio existentes.
- Para mudanças de banco, crie novas migrations Flyway; nunca altere migrations já aplicadas.
- Sugira testes unitários para regras de negócio e testes de integração para endpoints e persistência.
- Avalie validação de entrada, tratamento de erros, autenticação, autorização e proteção de dados sensíveis.
- Não registre senhas, tokens, dados sensíveis ou variáveis de ambiente em logs ou documentação.

## Comandos úteis

No Windows PowerShell:

```powershell
.\mvnw.cmd test
.\mvnw.cmd spring-boot:run

````
Use `.\mvnw.cmd clean verify` antes de considerar uma implementação concluída, quando eu solicitar implementação