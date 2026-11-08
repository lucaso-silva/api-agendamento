# Backlog de evolução

Este backlog foi organizado para evoluir a API sem pular fundamentos. A ordem proposta prioriza confiabilidade e qualidade antes de segurança e novas funcionalidades.

## Legenda

- **Prioridade:** crítica, alta, média ou baixa.
- **Dificuldade:** iniciante, intermediária ou avançada.
- **Dependências:** itens que devem estar concluídos antes de iniciar a tarefa.

## Etapa 1 — Base confiável de testes e regras existentes

### BL-01 — Isolar o ambiente de testes

- **Prioridade:** alta
- **Dificuldade:** iniciante/intermediária
- **Dependências:** nenhuma
- **Objetivo:** fazer com que os testes não dependam do PostgreSQL local configurado em `application.properties`.
- **Critérios de aceite:**
  - `mvn test` executa sem preparação manual de banco local;
  - migrations Flyway são aplicadas no banco de teste;
  - testes não alteram dados do ambiente de desenvolvimento;
  - a estratégia escolhida é documentada.
- **Testes sugeridos:** carga do contexto; teste de migration sobre banco vazio; teste de repositório com PostgreSQL de teste.

### BL-02 — Preservar o motivo do cancelamento na persistência

- **Prioridade:** crítica
- **Dificuldade:** iniciante
- **Dependências:** BL-01 recomendada
- **Objetivo:** garantir que o motivo informado ao cancelar seja salvo e recuperado corretamente.
- **Critérios de aceite:**
  - o agendamento passa para `CANCELADO`;
  - o motivo permanece persistido após nova leitura;
  - um segundo cancelamento é rejeitado;
  - a resposta e a documentação deixam claro se o motivo deve ser retornado.
- **Testes sugeridos:** unidade do domínio `Agendamento`; unidade de `CancelarAgendamentoImpl`; integração de gateway/repositório.

### BL-03 — Ampliar cobertura dos casos de uso atuais

- **Prioridade:** alta
- **Dificuldade:** iniciante
- **Dependências:** BL-01
- **Objetivo:** proteger as regras já implementadas contra regressões.
- **Critérios de aceite:**
  - há testes para cadastro e listagem de pacientes;
  - há testes para cancelamento, incluindo motivo ausente e agendamento inexistente;
  - cenários de domínio inválidos são testados sem Spring;
  - testes têm nomes orientados a comportamento.
- **Testes sugeridos:** JUnit 5 e Mockito para casos de uso; testes parametrizados para validações; factories de teste pequenas e legíveis.

### BL-04 — Tornar a validação temporal determinística

- **Prioridade:** média
- **Dificuldade:** intermediária
- **Dependências:** BL-03
- **Objetivo:** remover a dependência direta de `LocalDateTime.now()` dos casos de uso.
- **Critérios de aceite:**
  - a fonte de tempo pode ser controlada nos testes;
  - a regra de data futura continua válida;
  - timezone e precisão temporal estão documentados.
- **Testes sugeridos:** data passada; data exatamente igual ao instante atual; data futura; execução em timezone explícito.

## Etapa 2 — Contrato HTTP, consulta e persistência

### BL-05 — Padronizar validações e respostas de erro

- **Prioridade:** alta
- **Dificuldade:** intermediária
- **Dependências:** BL-03
- **Objetivo:** oferecer contratos previsíveis para entradas inválidas e falhas conhecidas.
- **Critérios de aceite:**
  - IDs aceitam somente valores positivos;
  - limites de tamanho são definidos para campos e filtros;
  - JSON inválido, enum inválido e parâmetro inválido retornam `ProblemDetail` consistente;
  - violações de unicidade não retornam detalhes internos do banco.
- **Testes sugeridos:** MockMvc para `400`, `404` e `409`; payload malformado; `status` inválido; carteirinha duplicada.

### BL-06 — Paginar e ordenar consultas

- **Prioridade:** alta
- **Dificuldade:** intermediária
- **Dependências:** BL-05
- **Objetivo:** evitar listas ilimitadas de pacientes e agendamentos.
- **Critérios de aceite:**
  - endpoints aceitam página, tamanho e ordenação controlada;
  - há tamanho máximo de página;
  - filtros continuam combináveis;
  - a resposta contém metadados suficientes para navegação.
- **Testes sugeridos:** primeira e segunda página; lista vazia; tamanho máximo; filtros combinados; ordenação inválida.

### BL-07 — Eliminar N+1 e revisar consultas JPA

- **Prioridade:** média
- **Dificuldade:** intermediária
- **Dependências:** BL-06
- **Objetivo:** garantir leitura eficiente de paciente e profissional ao listar agendamentos.
- **Critérios de aceite:**
  - a listagem não dispara uma consulta adicional por agendamento;
  - a estratégia escolhida (`fetch join`, projeção ou `EntityGraph`) é justificada;
  - consultas permanecem pagináveis quando aplicável.
- **Testes sugeridos:** integração com múltiplos agendamentos; inspeção controlada do número de consultas; teste de filtros.

### BL-08 — Reforçar integridade do banco

- **Prioridade:** alta
- **Dificuldade:** intermediária
- **Dependências:** BL-01 e BL-05
- **Objetivo:** fazer o banco proteger invariantes que não podem depender apenas da aplicação.
- **Critérios de aceite:**
  - novas migrations, sem alterar migrations já aplicadas;
  - índices para consultas frequentes são definidos e justificados;
  - constraints seguem as regras de domínio;
  - erro de integridade é traduzido pela API.
- **Testes sugeridos:** migration em banco limpo; tentativa de violar constraint; verificação de resposta HTTP correspondente.

## Etapa 3 — Concorrência e segurança

### BL-09 — Garantir exclusividade de agenda sob concorrência

- **Prioridade:** alta
- **Dificuldade:** avançada
- **Dependências:** BL-08
- **Objetivo:** impedir dois agendamentos ativos para o mesmo profissional e horário mesmo com requisições simultâneas.
- **Critérios de aceite:**
  - somente um agendamento ativo é persistido em disputa concorrente;
  - cancelados não bloqueiam a agenda;
  - a API retorna conflito previsível ao perdedor da disputa;
  - a decisão de banco é documentada.
- **Testes sugeridos:** integração concorrente usando PostgreSQL; tentativa dupla sequencial; tentativa dupla simultânea; caso com cancelamento prévio.

### BL-10 — Implementar autenticação e autorização

- **Prioridade:** alta
- **Dificuldade:** intermediária
- **Dependências:** definição de papéis de produto
- **Objetivo:** restringir acesso aos recursos da API.
- **Critérios de aceite:**
  - rotas não públicas exigem identidade autenticada;
  - papéis e permissões estão documentados;
  - senhas, tokens e dados sensíveis não aparecem em logs;
  - OpenAPI descreve o mecanismo de autenticação.
- **Testes sugeridos:** sem credencial retorna `401`; papel insuficiente retorna `403`; credencial válida permite o caso autorizado.

### BL-11 — Aplicar autorização por dono do recurso

- **Prioridade:** alta
- **Dificuldade:** avançada
- **Dependências:** BL-10 e definição de ownership
- **Objetivo:** impedir que um paciente consulte ou cancele agendamentos de outro paciente.
- **Critérios de aceite:**
  - identidade autenticada é relacionada ao recurso;
  - regras são aplicadas no caso de uso, não apenas na tela ou controller;
  - tentativa de acesso cruzado é bloqueada sem vazamento indevido de dados.
- **Testes sugeridos:** paciente proprietário; paciente não proprietário; recepcionista; profissional; administrador, conforme papéis escolhidos.

### BL-12 — Criar perfis e configuração segura de operação

- **Prioridade:** média
- **Dificuldade:** intermediária
- **Dependências:** BL-10 recomendada
- **Objetivo:** separar configurações de desenvolvimento, teste e produção.
- **Critérios de aceite:**
  - SQL detalhado fica desativado em produção;
  - segredos continuam vindos de ambiente ou gerenciador de segredos;
  - CORS é explícito e restrito às origens necessárias;
  - health checks não expõem dados sensíveis.
- **Testes sugeridos:** inicialização por perfil; endpoint de health protegido quando necessário; teste de CORS permitido e negado.

## Etapa 4 — Funcionalidades de agenda

### BL-13 — CRUD e inativação de profissionais

- **Prioridade:** média
- **Dificuldade:** intermediária
- **Dependências:** BL-05 e decisões de produto
- **Objetivo:** deixar de depender de dados iniciais fixos para profissionais.
- **Critérios de aceite:**
  - profissional pode ser cadastrado, consultado e atualizado;
  - inativação não apaga histórico;
  - profissional inativo não recebe novos agendamentos;
  - regras de identificadores profissionais são definidas quando aplicáveis.
- **Testes sugeridos:** cadastro válido; inativação; tentativa de novo agendamento para inativo; profissional inexistente.

### BL-14 — Especialidades e disponibilidade

- **Prioridade:** média
- **Dificuldade:** avançada
- **Dependências:** BL-13 e decisões de produto
- **Objetivo:** modelar jornada de trabalho, indisponibilidades e especialidades.
- **Critérios de aceite:**
  - profissional possui especialidade(s) e agenda configurável;
  - horários fora da jornada são rejeitados;
  - API consulta horários disponíveis;
  - folgas e bloqueios são respeitados.
- **Testes sugeridos:** horário dentro e fora da jornada; bloqueio excepcional; filtro por especialidade; agenda vazia.

### BL-15 — Modelar duração e sobreposição de consultas

- **Prioridade:** alta
- **Dificuldade:** avançada
- **Dependências:** BL-09 e BL-14
- **Objetivo:** impedir sobreposição de intervalos, não apenas coincidência exata de horário.
- **Critérios de aceite:**
  - agendamento possui duração ou início/fim claramente definidos;
  - sobreposições parciais são rejeitadas;
  - horários limítrofes são aceitos conforme regra documentada;
  - timezone é consistente em API e banco.
- **Testes sugeridos:** intervalo contido; intervalo que contém outro; bordas adjacentes; mudanças de horário de verão, se houver suporte a fusos.

### BL-16 — Remarcação e histórico de eventos

- **Prioridade:** média
- **Dificuldade:** avançada
- **Dependências:** BL-15 e decisões de política de cancelamento
- **Objetivo:** registrar ciclo de vida do agendamento sem apagar seu histórico.
- **Critérios de aceite:**
  - remarcação preserva vínculo com o agendamento de origem;
  - cancelamento, remarcação e criação ficam auditáveis;
  - transições inválidas são rejeitadas;
  - regras de prazo são documentadas.
- **Testes sugeridos:** transições válidas e inválidas; cadeia de remarcações; concorrência durante remarcação; histórico imutável.

### BL-17 — Notificações assíncronas

- **Prioridade:** baixa
- **Dificuldade:** avançada
- **Dependências:** BL-16 e escolha de canal
- **Objetivo:** avisar pacientes e profissionais sobre criação, cancelamento e lembretes.
- **Critérios de aceite:**
  - falha de notificação não desfaz a operação principal indevidamente;
  - envio é rastreável e idempotente;
  - dados pessoais são minimizados nos eventos e logs.
- **Testes sugeridos:** publicação de evento; falha transitória; reprocessamento; ausência de duplicidade.

## Etapa 5 — Observabilidade e entrega

### BL-18 — Adicionar observabilidade e qualidade contínua

- **Prioridade:** média
- **Dificuldade:** intermediária
- **Dependências:** BL-12
- **Objetivo:** diagnosticar falhas e manter qualidade ao longo da evolução.
- **Critérios de aceite:**
  - health, métricas e logs estruturados estão disponíveis de forma segura;
  - correlação de requisições pode ser investigada;
  - pipeline executa testes e validação do build;
  - dependências possuem verificação periódica de vulnerabilidades.
- **Testes sugeridos:** teste de health; verificação de ausência de dados sensíveis em logs; execução de `clean verify`; teste de smoke da API.
