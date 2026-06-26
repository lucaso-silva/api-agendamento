package com.lucas.agendamento.core.dto.paciente;

public record NovoPacienteInput(String nome,
                                String telefone,
                                String carteirinhaId) {
}
