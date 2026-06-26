package com.lucas.agendamento.core.gateway;

import com.lucas.agendamento.core.domain.Paciente;

import java.util.List;

public interface PacienteGateway {

    Paciente salvar(Paciente paciente);

    List<Paciente> listarPacientes();
}
