package com.lucas.agendamento.core.exception;

public class AgendamentoNotFoundException extends NotFoundException {
    private static final String CODE = "agendamento.agendamento-nao-encontrado";

    public AgendamentoNotFoundException(String message) {
        super(CODE, message);
    }
}
