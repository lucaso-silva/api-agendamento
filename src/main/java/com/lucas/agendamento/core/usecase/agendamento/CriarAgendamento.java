package com.lucas.agendamento.core.usecase.agendamento;

import com.lucas.agendamento.core.dto.agendamento.NovoAgendamentoInput;
import com.lucas.agendamento.core.dto.agendamento.AgendamentoOutput;

public interface CriarAgendamento {
    AgendamentoOutput criar(NovoAgendamentoInput input);
}
