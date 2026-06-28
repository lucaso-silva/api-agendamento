package com.lucas.agendamento.core.dto.agendamento;

import com.lucas.agendamento.core.domain.TipoAtendimento;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record NovoAgendamentoInput(@Schema(description = "Id do paciente", example = "1")
                                   @NotNull Long pacienteId,
                                   @Schema(description = "Id do profissional", example = "1")
                                   @NotNull Long profissionalId,
                                   @Schema(description = "Data e horário do agendamento. Deve ser uma data futura.", example = "2026-07-06T14:30:00")
                                   @NotNull LocalDateTime dataConsulta,
                                   @Schema(description = "Tipo do atendimento", example = "INICIAL")
                                   @NotNull TipoAtendimento tipo) {
}
