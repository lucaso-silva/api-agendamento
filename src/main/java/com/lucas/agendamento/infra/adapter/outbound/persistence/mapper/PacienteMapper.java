package com.lucas.agendamento.infra.adapter.outbound.persistence.mapper;

import com.lucas.agendamento.core.domain.Paciente;
import com.lucas.agendamento.infra.adapter.outbound.persistence.entity.PacienteEntity;

public class PacienteMapper {
    private PacienteMapper(){}

    public static PacienteEntity toEntity(Paciente paciente) {
        return PacienteEntity.builder()
                .id(paciente.getId())
                .nome(paciente.getNome())
                .telefone(paciente.getTelefone())
                .carteirinhaId(paciente.getCarteirinhaId())
                .build();
    }

    public static Paciente toDomain(PacienteEntity entity){
        return Paciente.restore(entity.getId(),
                entity.getNome(),
                entity.getTelefone(),
                entity.getCarteirinhaId());
    }
}
