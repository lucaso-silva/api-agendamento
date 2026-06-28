package com.lucas.agendamento.core.usecase.agendamento;

import com.lucas.agendamento.core.dto.agendamento.AgendamentoOutput;
import com.lucas.agendamento.core.dto.agendamento.MotivoCancelamentoInput;

public interface CancelarAgendamento {
    AgendamentoOutput cancelar(Long agendamentoId, MotivoCancelamentoInput motivo);
}
