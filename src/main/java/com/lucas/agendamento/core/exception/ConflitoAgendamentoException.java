package com.lucas.agendamento.core.exception;

public class ConflitoAgendamentoException extends BusinessException {
    private static final String CODE = "agendamento.conflito-agenda";
    private static final Integer HTTP_STATUS = 409;

    public ConflitoAgendamentoException(String message) {
        super(CODE,message,HTTP_STATUS);
    }
}