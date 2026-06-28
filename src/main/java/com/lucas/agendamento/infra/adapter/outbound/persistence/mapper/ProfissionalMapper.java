package com.lucas.agendamento.infra.adapter.outbound.persistence.mapper;

import com.lucas.agendamento.core.domain.Profissional;
import com.lucas.agendamento.infra.adapter.outbound.persistence.entity.ProfissionalEntity;

public class ProfissionalMapper {
    private ProfissionalMapper(){}

    public static Profissional toDomain(ProfissionalEntity entity){
        return Profissional.restore(entity.getId(),
                entity.getNome(),
                entity.getTelefone(),
                entity.getTipo());
    }

    public static ProfissionalEntity toEntity(Profissional profissional) {
        return ProfissionalEntity.builder()
                .id(profissional.getId())
                .nome(profissional.getNome())
                .telefone(profissional.getTelefone())
                .tipo(profissional.getTipo())
                .build();
    }
}
