package com.lucas.agendamento.core.usecase.agendamento;

import com.lucas.agendamento.core.domain.StatusAgendamento;

public record FiltroBusca(String paciente,
                          String profissional,
                          StatusAgendamento status) {
}
