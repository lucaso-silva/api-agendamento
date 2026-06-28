package com.lucas.agendamento.core.exception;

public class NotFoundException extends BusinessException {
    private static final Integer HTTP_STATUS = 404;

    public NotFoundException(String code, String message){
        super(code, message, HTTP_STATUS);
    }
}
