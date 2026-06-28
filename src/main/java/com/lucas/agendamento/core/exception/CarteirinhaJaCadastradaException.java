package com.lucas.agendamento.core.exception;

public class CarteirinhaJaCadastradaException extends BusinessException {
    private static final String CODE = "paciente.carteirinha-ja-cadastrada";
    private static final Integer HTTP_STATUS = 409;

    public CarteirinhaJaCadastradaException(String message) {
        super(CODE, message, HTTP_STATUS);
    }
}
