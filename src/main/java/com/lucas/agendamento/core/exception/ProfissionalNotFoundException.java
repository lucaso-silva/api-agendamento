package com.lucas.agendamento.core.exception;

public class ProfissionalNotFoundException extends NotFoundException {
    private static final String CODE = "agendamento.profissional-nao-encontrado";

    public ProfissionalNotFoundException(String message) {
        super(CODE,message);
    }
}
