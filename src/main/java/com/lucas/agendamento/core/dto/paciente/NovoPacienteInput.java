package com.lucas.agendamento.core.dto.paciente;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record NovoPacienteInput(@NotBlank @Length(min = 3) String nome,
                                @NotBlank @Length(min = 8, max = 15) String telefone,
                                String carteirinhaId) {
}
