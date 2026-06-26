package com.lucas.agendamento.core.dto.paciente;

import com.lucas.agendamento.core.domain.Paciente;

public record PacienteOutput(Long id, String nome) {
    public static PacienteOutput from(Paciente paciente) {

        return new PacienteOutput(paciente.getId(), paciente.getNome());
    }
}
