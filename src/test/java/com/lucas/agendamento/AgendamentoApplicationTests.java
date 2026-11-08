package com.lucas.agendamento;

import com.lucas.agendamento.infra.adapter.outbound.persistence.entity.PacienteEntity;
import com.lucas.agendamento.infra.adapter.outbound.persistence.repository.PacienteRepository;
import jakarta.persistence.EntityManager;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationState;
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

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
class AgendamentoApplicationTests {

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

	@Test
	void contextLoads() {
	}

    @Test
    void migrationsV1AndV2WereApplied(@Autowired Flyway flyway){
        var appliedMigrations = flyway.info().applied();

        for (String version : new String[]{"1", "2"}){
            boolean migrationSucceeded = Arrays.stream(appliedMigrations)
                    .anyMatch(migration -> migration.getVersion() != null
                    && version.equals(migration.getVersion().getVersion())
                    && migration.getState() == MigrationState.SUCCESS);

            assertTrue(migrationSucceeded, "Migration V" + version + " was not applied successfully");
        }
    }

    @Test
    @Transactional
    void successfullyPersistsAPatient(@Autowired PacienteRepository pacienteRepository,
                                       @Autowired EntityManager entityManager){
        final var NAME = "Paciente Test";
        final var PHONE = "81-33004455";
        final var CARTEIRINHA_ID ="111-222-333";

        var patient = PacienteEntity.builder()
                .nome(NAME)
                .telefone(PHONE)
                .carteirinhaId(CARTEIRINHA_ID)
                .build();

        var savedPatient = pacienteRepository.save(patient);

        entityManager.flush();
        entityManager.clear();

        var retrievedPatient = pacienteRepository.findById(savedPatient.getId())
                        .orElseThrow(()-> new AssertionError("Saved patient was not found"));

        assertEquals(NAME, retrievedPatient.getNome());
        assertEquals(PHONE, retrievedPatient.getTelefone());
        assertEquals(CARTEIRINHA_ID, retrievedPatient.getCarteirinhaId());
    }

}
