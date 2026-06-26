package com.lucas.agendamento.core.domain.exception;

public class RequiredFieldException extends RuntimeException {
    public RequiredFieldException(String message) {
        super(message);
    }
}
