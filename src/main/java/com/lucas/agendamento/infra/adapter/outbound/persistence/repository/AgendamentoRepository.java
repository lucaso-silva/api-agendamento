package com.lucas.agendamento.infra.adapter.outbound.persistence.repository;

import com.lucas.agendamento.core.domain.StatusAgendamento;
import com.lucas.agendamento.infra.adapter.outbound.persistence.entity.AgendamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface AgendamentoRepository extends JpaRepository<AgendamentoEntity, Long> {
    boolean existsByProfissional_IdAndDataAgendamentoAndStatus(Long id, LocalDateTime dataAgendamento, StatusAgendamento status);
}
