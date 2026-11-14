package com.lucas.agendamento.infra.adapter.outbound.persistence;

import com.lucas.agendamento.core.gateway.AgendamentoGateway;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

import static com.lucas.agendamento.core.domain.StatusAgendamento.CANCELADO;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
class AgendamentoGatewayImplIntegrationTest {

    @Container
    private static final PostgreSQLContainer postgresqlContainer = new PostgreSQLContainer("postgres:16")
            .withDatabaseName("agendamento")
            .withUsername("user")
            .withPassword("pass1234");

    @DynamicPropertySource
    private static void registerPostgresqlProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", postgresqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresqlContainer::getUsername);
        registry.add("spring.datasource.password", postgresqlContainer::getPassword);
    }

    @Autowired
    private AgendamentoGateway agendamentoGateway;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    void devePersistirMotivoCancelamentoAoRecuperarAgendamentoNovamente(){
        var agendamentoId = 1L;
        var motivoCancelamento = "Paciente solicitou cancelamento";

        var agendamento = agendamentoGateway.buscarPorId(agendamentoId)
                .orElseThrow(()-> new AssertionError("Saved patient was not found"));
        agendamento.cancelarAgendamento(motivoCancelamento);
        agendamentoGateway.salvar(agendamento);

        entityManager.flush();
        entityManager.clear();

        var agendamentoCancelado = agendamentoGateway.buscarPorId(agendamentoId)
                .orElseThrow();

        assertEquals(CANCELADO, agendamentoCancelado.getStatus());
        assertEquals(motivoCancelamento, agendamentoCancelado.getMotivoCancelamento());
    }
}
