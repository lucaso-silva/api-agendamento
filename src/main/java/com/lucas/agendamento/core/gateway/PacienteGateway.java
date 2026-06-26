package com.lucas.agendamento.core.gateway;

import com.lucas.agendamento.core.domain.Paciente;

import java.util.List;
import java.util.Optional;

public interface PacienteGateway {

    Paciente salvar(Paciente paciente);

    List<Paciente> listarPacientes();

    Optional<Paciente> buscarPorId(Long id);
}
