package com.lucas.agendamento.core.usecase.paciente;

import com.lucas.agendamento.core.dto.paciente.PacienteOutput;

import java.util.List;

public interface ListarPacientes {
    List<PacienteOutput> listar();
}
