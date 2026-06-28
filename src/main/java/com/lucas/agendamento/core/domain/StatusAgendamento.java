package com.lucas.agendamento.core.domain;

public enum StatusAgendamento {
    AGENDADO,
    CANCELADO;

    public static StatusAgendamento from(String status){
        if(status == null || status.isBlank()) return null;

        return valueOf(status.trim().toUpperCase());
    }
}
