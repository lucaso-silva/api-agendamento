package com.lucas.agendamento.core.domain.exception;

import lombok.Getter;

@Getter
public class DomainException extends RuntimeException{
    private final String code;
    private final Integer httpStatus;

    public DomainException(String code, String message, Integer httpStatus){
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
    }
}
