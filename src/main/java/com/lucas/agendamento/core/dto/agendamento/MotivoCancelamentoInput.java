package com.lucas.agendamento.core.dto.agendamento;

import jakarta.validation.constraints.NotBlank;

public record MotivoCancelamentoInput(@NotBlank String motivo) {
}
