package com.lucas.agendamento.factory;

import com.lucas.agendamento.core.domain.*;
import com.lucas.agendamento.core.dto.agendamento.NovoAgendamentoInput;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.lucas.agendamento.core.domain.StatusAgendamento.*;
import static com.lucas.agendamento.core.domain.TipoAtendimento.INICIAL;
import static com.lucas.agendamento.core.domain.TipoProfissional.MEDICO;

public class AgendamentoFactory {
    private AgendamentoFactory(){}

    public static NovoAgendamentoInput agendamentoInput() {
        var dataAgendamento = dataFutura();

        return new NovoAgendamentoInput(1L,
                2L,
                dataAgendamento,
                INICIAL);
    }

    public static Agendamento agendamento(){
        return Agendamento.restore(1L,
                paciente(),
                medico(),
                dataFutura(),
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

    public static NovoAgendamentoInput agendamentoInputDataPassada() {
        var dataAgendamento = dataPassada();

        return new NovoAgendamentoInput(1L,
                2L,
                dataAgendamento,
                INICIAL);
    }

    private static LocalDateTime dataFutura(){
        var dataAtual = LocalDate.of(LocalDate.now().getYear(),
                LocalDate.now().getMonth(),
                LocalDate.now().getDayOfMonth());
        var dataFutura = dataAtual.plusDays(7);

        return LocalDateTime.of(dataFutura.getYear(),
                dataFutura.getMonth(),
                dataFutura.getDayOfMonth(),
                10,00);
    }

    private static LocalDateTime dataPassada(){
        return LocalDateTime.now().minusDays(1);
    }
}
