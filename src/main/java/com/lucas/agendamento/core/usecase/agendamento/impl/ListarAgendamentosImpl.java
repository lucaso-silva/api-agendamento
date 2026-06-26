package com.lucas.agendamento.core.usecase.agendamento.impl;

import com.lucas.agendamento.core.dto.agendamento.AgendamentoOutput;
import com.lucas.agendamento.core.gateway.AgendamentoGateway;
import com.lucas.agendamento.core.usecase.agendamento.ListarAgendamentos;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
@Transactional(readOnly = true)
public class ListarAgendamentosImpl implements ListarAgendamentos {

    private final AgendamentoGateway agendamentoGateway;

    @Override
    public List<AgendamentoOutput> listar() {
        return agendamentoGateway.listar().stream()
                .map(AgendamentoOutput::from)
                .toList();
    }
}
