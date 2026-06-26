package com.lucas.agendamento.core.usecase.paciente;

import com.lucas.agendamento.core.dto.paciente.NovoPacienteInput;
import com.lucas.agendamento.core.dto.paciente.PacienteOutput;

public interface CadastrarPaciente {
    PacienteOutput cadastrar(NovoPacienteInput input);
}
