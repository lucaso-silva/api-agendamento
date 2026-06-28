package com.lucas.agendamento.infra.adapter.outbound.persistence.repository;

import com.lucas.agendamento.infra.adapter.outbound.persistence.entity.AgendamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface AgendamentoRepository extends JpaRepository<AgendamentoEntity, Long> {
    boolean existsByProfissional_IdAndDataAgendamento(Long id, LocalDateTime dataAgendamento);
}
