package com.lucas.agendamento.core.domain.exception;

public class RequiredFieldException extends DomainException {
    private static final String CODE = "agendamento.campo-obrigatório";
    private static final Integer HTTP_STATUS = 400;

    public RequiredFieldException(String message) {
        super(CODE, message, HTTP_STATUS);
    }
}
