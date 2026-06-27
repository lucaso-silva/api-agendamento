package com.lucas.agendamento.core.dto.agendamento;

import com.lucas.agendamento.core.domain.Agendamento;
import com.lucas.agendamento.core.domain.StatusAgendamento;

import java.time.LocalDateTime;

public record AgendamentoOutput(Long idAgendamento,
                                String pacienteNome,
                                String profissionalNome,
                                LocalDateTime dataConsulta,
                                StatusAgendamento status) {
    public static AgendamentoOutput from(Agendamento agendamento) {
        return new AgendamentoOutput(agendamento.getId(),
                agendamento.getPaciente().getNome(),
                agendamento.getProfissional().getNome(),
                agendamento.getDataAgendamento(),
                agendamento.getStatus());
    }
}
