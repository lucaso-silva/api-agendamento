package com.lucas.agendamento.core.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final String code;
    private final Integer httpStatus;

    public BusinessException(String code, String message, Integer httpStatus){
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
    }
}
