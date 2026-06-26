package com.lucas.agendamento.core.usecase.paciente.impl;

import com.lucas.agendamento.core.dto.paciente.PacienteOutput;
import com.lucas.agendamento.core.gateway.PacienteGateway;
import com.lucas.agendamento.core.usecase.paciente.ListarPacientes;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
@Transactional(readOnly = true)
public class ListarPacientesImpl implements ListarPacientes {
    private final PacienteGateway pacienteGateway;

    @Override
    public List<PacienteOutput> listar() {

        return pacienteGateway.listarPacientes()
                .stream()
                .map(PacienteOutput::from)
                .toList();
    }
}
