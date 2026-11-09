package com.lucas.agendamento.core.domain;

import com.lucas.agendamento.core.domain.exception.AgendamentoCanceladoException;
import com.lucas.agendamento.core.domain.exception.RequiredFieldException;
import com.lucas.agendamento.factory.AgendamentoFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AgendamentoTest {

    private static final String MOTIVO_CANCELAMENTO = "Paciente solicitou cancelamento";

    @Test
    void deveCancelarUmAgendamentoEArmazenarMotivo(){
        var agendamento = AgendamentoFactory.agendamento();
        assertEquals(StatusAgendamento.AGENDADO, agendamento.getStatus());
        assertNull(agendamento.getMotivoCancelamento());

        agendamento.cancelarAgendamento(MOTIVO_CANCELAMENTO);

        assertEquals(StatusAgendamento.CANCELADO, agendamento.getStatus());
        assertEquals(MOTIVO_CANCELAMENTO, agendamento.getMotivoCancelamento());

    }

    @Test
    void naoDevePermitirSegundoCancelamento(){
        var agendamento = AgendamentoFactory.agendamento();

        assertEquals(StatusAgendamento.AGENDADO, agendamento.getStatus());

        agendamento.cancelarAgendamento(MOTIVO_CANCELAMENTO);

        assertEquals(StatusAgendamento.CANCELADO, agendamento.getStatus());

        assertThrows(
                AgendamentoCanceladoException.class,
                ()-> agendamento.cancelarAgendamento(MOTIVO_CANCELAMENTO)
        );

        assertEquals(StatusAgendamento.CANCELADO, agendamento.getStatus());
        assertEquals(MOTIVO_CANCELAMENTO, agendamento.getMotivoCancelamento());
    }

    @Test
    void deveLancarExcecaoQuandoMotivoForNulo(){
        var agendamento = AgendamentoFactory.agendamento();

        assertThrows(
                RequiredFieldException.class,
                ()-> agendamento.cancelarAgendamento(null)
        );
        assertEquals(StatusAgendamento.AGENDADO, agendamento.getStatus());
    }

    @Test
    void deveLancarExcecaoQuandoMotivoForVazio(){
        var agendamento = AgendamentoFactory.agendamento();

        assertThrows(
                RequiredFieldException.class,
                ()-> agendamento.cancelarAgendamento("")
        );
        assertEquals(StatusAgendamento.AGENDADO, agendamento.getStatus());
    }

    @Test
    void deveLancarExcecaoQuandoMotivoContiverApenasEspacos(){
        var agendamento = AgendamentoFactory.agendamento();

        assertThrows(
                RequiredFieldException.class,
                ()-> agendamento.cancelarAgendamento("    ")
        );
        assertEquals(StatusAgendamento.AGENDADO, agendamento.getStatus());
    }
}
