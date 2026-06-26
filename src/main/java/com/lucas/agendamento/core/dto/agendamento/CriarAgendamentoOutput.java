package com.lucas.agendamento.core.dto.agendamento;

import com.lucas.agendamento.core.domain.Agendamento;
import com.lucas.agendamento.core.domain.StatusAgendamento;

import java.time.LocalDateTime;

public record CriarAgendamentoOutput(Long idAgendamento,
                                     String pacienteNome,
                                     String profissionalNome,
                                     LocalDateTime dataConsulta,
                                     StatusAgendamento status) {
    public static CriarAgendamentoOutput from(Agendamento agendamento) {
        return new CriarAgendamentoOutput(agendamento.getId(),
                agendamento.getPaciente().getNome(),
                agendamento.getProfissional().getNome(),
                agendamento.getDataConsulta(),
                agendamento.getStatus());
    }
}
