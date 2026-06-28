package com.lucas.agendamento.core.dto.agendamento;

import com.lucas.agendamento.core.domain.TipoAtendimento;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record NovoAgendamentoInput(@NotNull Long pacienteId,
                                   @NotNull Long profissionalId,
                                   @NotNull LocalDateTime dataConsulta,
                                   @NotNull TipoAtendimento tipo) {
}
