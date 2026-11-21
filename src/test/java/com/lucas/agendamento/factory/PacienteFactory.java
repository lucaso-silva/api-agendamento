package com.lucas.agendamento.factory;

import com.lucas.agendamento.core.domain.Paciente;
import com.lucas.agendamento.core.dto.paciente.NovoPacienteInput;

import java.util.List;

public class PacienteFactory {
    private PacienteFactory(){}

    public static NovoPacienteInput pacienteInputCarteirinha(){
        return new NovoPacienteInput("Joao da Silva",
                "(81)3333-3333",
                "123456789");
    }

    public static Paciente pacienteCarteirinha() {
        var paciente = pacienteInputCarteirinha();

        return Paciente.restore(1L,
                paciente.nome(),
                paciente.telefone(),
                paciente.carteirinhaId());
    }

    public static NovoPacienteInput pacienteInputSemCarteirinha(){
        return new NovoPacienteInput("Maria Santana",
                "(81)3333-4444",
                null);
    }

    public static Paciente pacienteSemCarteirinha() {
        var paciente = pacienteInputSemCarteirinha();

        return Paciente.restore(2L,
                paciente.nome(),
                paciente.telefone(),
                paciente.carteirinhaId());
    }

    public static List<Paciente> listaPacientes() {
        var pacienteUm = pacienteCarteirinha();
        var pacienteDois = pacienteSemCarteirinha();

        return List.of(pacienteUm, pacienteDois);
    }

}
