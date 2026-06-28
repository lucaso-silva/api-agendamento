package com.lucas.agendamento.infra.adapter.outbound.persistence.repository;

import com.lucas.agendamento.infra.adapter.outbound.persistence.entity.PacienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<PacienteEntity, Long> {
    boolean existsByCarteirinhaId(String carteirinhaId);
}
