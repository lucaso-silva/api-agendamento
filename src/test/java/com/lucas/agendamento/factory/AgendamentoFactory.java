package com.lucas.agendamento.factory;

import com.lucas.agendamento.core.domain.Agendamento;
import com.lucas.agendamento.core.domain.Paciente;
import com.lucas.agendamento.core.domain.Profissional;
import com.lucas.agendamento.core.dto.agendamento.NovoAgendamentoInput;

import java.time.LocalDateTime;

import static com.lucas.agendamento.core.domain.StatusAgendamento.AGENDADO;
import static com.lucas.agendamento.core.domain.TipoAtendimento.INICIAL;
import static com.lucas.agendamento.core.domain.TipoProfissional.MEDICO;

public class AgendamentoFactory {
    private AgendamentoFactory(){}

    public static NovoAgendamentoInput agendamentoInput(LocalDateTime dataAgendamento) {

        return new NovoAgendamentoInput(1L,
                2L,
                dataAgendamento,
                INICIAL);
    }

    public static Agendamento agendamento(LocalDateTime dataAgendamento){
        return Agendamento.restore(1L,
                paciente(),
                medico(),
                dataAgendamento,
                INICIAL,
                AGENDADO,
                null);
    }

    public static Paciente paciente(){
        return Paciente.restore(1L,
                "Nome paciente",
                "1234-5678",
                "10002026");
    }

    public static Profissional medico(){
        return Profissional.restore(2L,
                "Nome medico",
                "9876-5432",
                MEDICO);
    }
}
