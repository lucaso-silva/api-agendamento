package com.lucas.agendamento.core.exception;

public class PacienteNotFoundException extends NotFoundException {
    private static final String CODE = "agendamento.paciente-nao-encontrado";

    public PacienteNotFoundException(String message) {
        super(CODE, message);
    }
}
