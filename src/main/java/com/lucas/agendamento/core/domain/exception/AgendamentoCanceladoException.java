package com.lucas.agendamento.core.domain.exception;

public class AgendamentoCanceladoException extends DomainException {
    private static final  String CODE = "agendamento.agendamento-cancelado";
    private static final Integer HTTP_STATUS = 409;

    public AgendamentoCanceladoException(String message) {
        super(CODE, message, HTTP_STATUS);
    }
}
