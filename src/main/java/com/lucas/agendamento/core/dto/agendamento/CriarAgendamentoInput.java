package com.lucas.agendamento.core.dto.agendamento;

import com.lucas.agendamento.core.domain.TipoAtendimento;

import java.time.LocalDateTime;

public record CriarAgendamentoInput(Long pacienteId,
                                    Long profissionalId,
                                    LocalDateTime dataConsulta,
                                    TipoAtendimento tipo) {
}
