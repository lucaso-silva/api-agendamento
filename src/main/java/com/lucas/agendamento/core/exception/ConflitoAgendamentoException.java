package com.lucas.agendamento.core.exception;

public class ConflitoAgendamentoException extends RuntimeException {
    public ConflitoAgendamentoException(String message) {
        super(message);
    }
}