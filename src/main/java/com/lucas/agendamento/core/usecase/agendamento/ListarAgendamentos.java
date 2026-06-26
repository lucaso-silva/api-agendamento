package com.lucas.agendamento.core.usecase.agendamento;

import com.lucas.agendamento.core.dto.agendamento.AgendamentoOutput;

import java.util.List;

public interface ListarAgendamentos {
    List<AgendamentoOutput> listar();
}
