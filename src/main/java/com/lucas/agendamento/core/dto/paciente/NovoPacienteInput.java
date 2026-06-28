package com.lucas.agendamento.core.dto.paciente;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record NovoPacienteInput(@Schema(description = "Nome completo do paciente", example = "Joao da Silva")
                                @NotBlank @Length(min = 3) String nome,
                                @Schema(description = "Telefone para contato", example = "(81)3333-3333")
                                @NotBlank @Length(min = 8, max = 15) String telefone,
                                @Schema(description = "Número da carteirinha do convênio", example = "123456789")
                                String carteirinhaId) {
}
