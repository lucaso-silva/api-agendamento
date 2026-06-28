package com.lucas.agendamento.infra.adapter.outbound.persistence.repository;

import com.lucas.agendamento.infra.adapter.outbound.persistence.entity.ProfissionalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfissionalRepository extends JpaRepository<ProfissionalEntity, Long> {
}
