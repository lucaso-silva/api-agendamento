package com.lucas.agendamento.core.dto.agendamento;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record MotivoCancelamentoInput(@Schema(description = "Motivo informado para o cancelamento do agendamento", example = "Paciente solicitou cancelamento")
                                      @NotBlank String motivo) {
}
