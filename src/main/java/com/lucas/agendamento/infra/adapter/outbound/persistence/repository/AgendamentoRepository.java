package com.lucas.agendamento.infra.adapter.outbound.persistence.repository;

import com.lucas.agendamento.core.domain.StatusAgendamento;
import com.lucas.agendamento.infra.adapter.outbound.persistence.entity.AgendamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AgendamentoRepository extends JpaRepository<AgendamentoEntity, Long> {

    @Query("""
            SELECT a
               FROM AgendamentoEntity a
               WHERE (:paciente IS NULL OR LOWER(a.paciente.nome) LIKE :paciente)
                   AND (:profissional IS NULL OR LOWER(a.profissional.nome) LIKE :profissional)
                   AND (:status IS NULL OR a.status = :status)
               ORDER BY a.dataAgendamento
            """)
    List<AgendamentoEntity> buscar(@Param("paciente") String paciente,
                                   @Param("profissional") String profissional,
                                   @Param("status") StatusAgendamento status);

    boolean existsByProfissional_IdAndDataAgendamentoAndStatus(Long id, LocalDateTime dataAgendamento, StatusAgendamento status);
}
