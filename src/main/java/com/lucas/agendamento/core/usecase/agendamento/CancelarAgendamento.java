package com.lucas.agendamento.core.usecase.agendamento;

import com.lucas.agendamento.core.dto.agendamento.AgendamentoOutput;

public interface CancelarAgendamento {
    AgendamentoOutput cancelar(Long agendamentoId, String motivo);
}
