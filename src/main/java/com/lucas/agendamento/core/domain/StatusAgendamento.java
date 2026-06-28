package com.lucas.agendamento.core.domain;

import com.lucas.agendamento.core.exception.BusinessException;

public enum StatusAgendamento {
    AGENDADO,
    CANCELADO;

    public static StatusAgendamento from(String status){
        if(status == null || status.isBlank()) return null;

        try{
            return StatusAgendamento.valueOf(status.trim().toUpperCase());

        }catch(IllegalArgumentException ex){
            throw new BusinessException("agendamento.status-invalido",
                    "Status '%s' invalido".formatted(status),
                    400);
        }

    }
}
