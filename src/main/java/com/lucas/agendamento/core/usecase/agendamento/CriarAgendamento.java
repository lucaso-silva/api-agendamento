package com.lucas.agendamento.core.usecase.agendamento;

import com.lucas.agendamento.core.dto.agendamento.CriarAgendamentoInput;
import com.lucas.agendamento.core.dto.agendamento.CriarAgendamentoOutput;

public interface CriarAgendamento {
    CriarAgendamentoOutput criar(CriarAgendamentoInput input);
}
