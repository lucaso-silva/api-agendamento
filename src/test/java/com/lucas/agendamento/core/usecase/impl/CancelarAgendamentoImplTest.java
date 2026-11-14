package com.lucas.agendamento.core.usecase.impl;

import com.lucas.agendamento.core.domain.Agendamento;
import com.lucas.agendamento.core.domain.exception.AgendamentoCanceladoException;
import com.lucas.agendamento.core.domain.exception.RequiredFieldException;
import com.lucas.agendamento.core.dto.agendamento.MotivoCancelamentoInput;
import com.lucas.agendamento.core.exception.AgendamentoNotFoundException;
import com.lucas.agendamento.core.gateway.AgendamentoGateway;
import com.lucas.agendamento.core.usecase.agendamento.impl.CancelarAgendamentoImpl;
import com.lucas.agendamento.factory.AgendamentoFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.lucas.agendamento.core.domain.StatusAgendamento.CANCELADO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CancelarAgendamentoImplTest {
    @Mock
    private AgendamentoGateway agendamentoGateway;

    @InjectMocks
    private CancelarAgendamentoImpl cancelarAgendamento;

    @Test
    void deveCancelarAgendamento(){
        var agendamento = AgendamentoFactory.agendamento();
        var agendamentoId = agendamento.getId();
        var motivoCancelamento = "Paciente solicitou cancelamento";

        ArgumentCaptor<Agendamento> captor = ArgumentCaptor.forClass(Agendamento.class);

        when(agendamentoGateway.buscarPorId(agendamentoId))
                .thenReturn(Optional.of(agendamento));
        when(agendamentoGateway.salvar(any(Agendamento.class)))
                .thenReturn(agendamento);

        var output = cancelarAgendamento.cancelar(agendamentoId, new MotivoCancelamentoInput(motivoCancelamento));

        assertNotNull(output);
        assertAll(
                ()-> assertEquals(agendamento.getId() , output.idAgendamento()),
                ()-> assertEquals(agendamento.getPaciente().getNome(), output.pacienteNome()),
                ()-> assertEquals(agendamento.getProfissional().getNome(), output.profissionalNome()),
                ()-> assertEquals(agendamento.getDataAgendamento(), output.dataConsulta()),
                ()-> assertEquals(CANCELADO, output.status())
        );

        verify(agendamentoGateway).buscarPorId(agendamentoId);
        verify(agendamentoGateway).salvar(captor.capture());

        var agendamentoCancelado = captor.getValue();

        assertAll(
                ()-> assertEquals(agendamentoId, agendamentoCancelado.getId()),
                ()-> assertEquals(agendamento.getDataAgendamento(), agendamentoCancelado.getDataAgendamento()),
                ()-> assertEquals(CANCELADO, agendamentoCancelado.getStatus()),
                ()-> assertEquals(motivoCancelamento, agendamentoCancelado.getMotivoCancelamento())
        );

        verifyNoMoreInteractions(agendamentoGateway);
    }

    @Test
    void deveLancarExcecaoQuandoAgendamentoNaoEncontrado(){
        var agendamentoId = 1L;
        var motivoCancelamento = new MotivoCancelamentoInput("Paciente solicitou cancelamento");

        when(agendamentoGateway.buscarPorId(agendamentoId))
                .thenReturn(Optional.empty());

        assertThrows(
                AgendamentoNotFoundException.class,
                ()-> cancelarAgendamento.cancelar(agendamentoId,motivoCancelamento)
        );

        verify(agendamentoGateway).buscarPorId(agendamentoId);
        verify(agendamentoGateway, never()).salvar(any(Agendamento.class));
        verifyNoMoreInteractions(agendamentoGateway);
    }

    @Test
    void deveLancarExcecaoQuandoAgendamentoJaCancelado(){
        var agendamento = AgendamentoFactory.agendamento();
        var motivoCancelamento = "Paciente solicitou cancelamento";
        agendamento.cancelarAgendamento(motivoCancelamento);
        var agendamentoId = agendamento.getId();

        when(agendamentoGateway.buscarPorId(agendamentoId))
                .thenReturn(Optional.of(agendamento));

        assertThrows(
                AgendamentoCanceladoException.class,
                ()->cancelarAgendamento.cancelar(agendamentoId, new MotivoCancelamentoInput(motivoCancelamento))
        );

        verify(agendamentoGateway).buscarPorId(agendamentoId);
        verify(agendamentoGateway, never()).salvar(any(Agendamento.class));
        verifyNoMoreInteractions(agendamentoGateway);
    }

    @Test
    void deveLancarExcecaoQuandoMotivoForVazio(){
        var agendamento = AgendamentoFactory.agendamento();
        var motivoCancelamentoInvalido = "";
        var agendamentoId = agendamento.getId();

        when(agendamentoGateway.buscarPorId(agendamentoId))
                .thenReturn(Optional.of(agendamento));

        assertThrows(
               RequiredFieldException.class,
                ()-> cancelarAgendamento.cancelar(agendamentoId, new MotivoCancelamentoInput(motivoCancelamentoInvalido))
        );

        verify(agendamentoGateway).buscarPorId(agendamentoId);
        verify(agendamentoGateway, never()).salvar(any(Agendamento.class));
        verifyNoMoreInteractions(agendamentoGateway);
    }
}
