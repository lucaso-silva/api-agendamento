package com.lucas.agendamento.core.exception;

public class DataInvalidaException extends BusinessException {
    private static final String CODE = "agendamento.data-invalida";
    private static final Integer HTTP_STATUS = 409;

    public DataInvalidaException(String message) {
        super(CODE,message,HTTP_STATUS);
    }
}
