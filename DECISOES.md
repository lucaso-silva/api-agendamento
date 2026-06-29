## Decisões técnicas

* Utilizar uma arquitetura inspirada nos princípios da Clean Architecture, visando: 
  * baixo acoplamento com frameworks e tecnologias externas,
  * separação clara de responsabilidades, e 
  * uma base sólida para futuras expansões
* Modelar pacientes e profissionais como entidades distintas, reduzindo a complexidade da solução, considerando o prazo disponível para desenvolvimento
* Utilizar Flyway para versionamento do banco de dados

### O que foi priorizado

* Implementar uma API backend robusta e de fácil manutenção
* Garantir a implementação e validação das principais regras de negócio relacionadas aos agendamentos
* Documentar a API utilizando Swagger/OpenAPI.

### O que ficou de fora

* Endpoints para gerenciamento de profissionais
* Testes unitários dos casos de uso relacionados a pacientes
* Testes de integração e controllers
* Interface frontend para consumo da API

## Uso IA

A IA foi utilizada como ferramenta de apoio durante o desenvolvimento, principalmente para:

* Validar a modelagem inicial das entidades e a estrutura arquitetural
* Auxiliar no troubleshooting e refinamento da consulta utilizada nos filtros de agendamento
* Revisar e auxiliar na elaboração da documentação do projeto: `README.md` e `DECISOES.md`

Todas as sugestões fornecidas foram analisadas, adaptadas quando necessário e validadas manualmente antes de serem incorporadas ao projeto.
