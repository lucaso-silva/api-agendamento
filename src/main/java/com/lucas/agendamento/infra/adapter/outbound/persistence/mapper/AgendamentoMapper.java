package com.lucas.agendamento.infra.adapter.outbound.persistence.mapper;

import com.lucas.agendamento.core.domain.Agendamento;
import com.lucas.agendamento.infra.adapter.outbound.persistence.entity.AgendamentoEntity;

public class AgendamentoMapper {
    private AgendamentoMapper(){}

    public static AgendamentoEntity toEntity(Agendamento agendamento){
        var pacienteEntity = PacienteMapper.toEntity(agendamento.getPaciente());
        var profissionalEntity = ProfissionalMapper.toEntity(agendamento.getProfissional());

        return AgendamentoEntity.builder()
                .id(agendamento.getId())
                .paciente(pacienteEntity)
                .profissional(profissionalEntity)
                .dataAgendamento(agendamento.getDataAgendamento())
                .tipo(agendamento.getTipo())
                .status(agendamento.getStatus())
                .build();
    }

    public static Agendamento toDomain(AgendamentoEntity entity){
        var paciente = PacienteMapper.toDomain(entity.getPaciente());
        var profissional = ProfissionalMapper.toDomain(entity.getProfissional());

        return Agendamento.restore(entity.getId(),
                paciente,
                profissional,
                entity.getDataAgendamento(),
                entity.getTipo(),
                entity.getStatus(),
                entity.getMotivoCancelamento());
    }
}
