package com.lucas.agendamento.core.usecase.impl;

import com.lucas.agendamento.core.domain.Agendamento;
import com.lucas.agendamento.core.exception.ConflitoAgendamentoException;
import com.lucas.agendamento.core.exception.DataInvalidaException;
import com.lucas.agendamento.core.exception.PacienteNotFoundException;
import com.lucas.agendamento.core.exception.ProfissionalNotFoundException;
import com.lucas.agendamento.core.gateway.AgendamentoGateway;
import com.lucas.agendamento.core.gateway.PacienteGateway;
import com.lucas.agendamento.core.gateway.ProfissionalGateway;
import com.lucas.agendamento.core.usecase.agendamento.impl.CriarAgendamentoImpl;
import com.lucas.agendamento.factory.AgendamentoFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.lucas.agendamento.core.domain.StatusAgendamento.AGENDADO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CriarAgendamentoImplTest {
    @Mock
    private AgendamentoGateway agendamentoGateway;
    @Mock
    private PacienteGateway pacienteGateway;
    @Mock
    private ProfissionalGateway profissionalGateway;

    @InjectMocks
    private CriarAgendamentoImpl criarAgendamento;

    @Test
    void deveCriarAgendamento(){
        var novoAgendamentoInput = AgendamentoFactory.agendamentoInput();
        var agendamento = AgendamentoFactory.agendamento();
        var paciente = AgendamentoFactory.paciente();
        var profissional = AgendamentoFactory.medico();
        var pacienteId = paciente.getId();
        var profissionalId = profissional.getId();
        var dataAgendamento = novoAgendamentoInput.dataConsulta();

        ArgumentCaptor<Agendamento> argumentoCaptor = ArgumentCaptor.forClass(Agendamento.class);

        when(agendamentoGateway.haConflitoDeAgenda(profissionalId, dataAgendamento))
                .thenReturn(false);
        when(pacienteGateway.buscarPorId(pacienteId))
                .thenReturn(Optional.of(paciente));
        when(profissionalGateway.buscarPorId(profissionalId))
                .thenReturn(Optional.of(profissional));
        when(agendamentoGateway.salvar(any(Agendamento.class)))
                .thenReturn(agendamento);

        var output = criarAgendamento.criar(novoAgendamentoInput);

        assertNotNull(output);
        assertAll(
                ()-> assertEquals(agendamento.getId(),output.idAgendamento()),
                ()-> assertEquals(agendamento.getPaciente().getNome(),output.pacienteNome()),
                ()-> assertEquals(agendamento.getProfissional().getNome(),output.profissionalNome()),
                ()-> assertEquals(agendamento.getDataAgendamento(),output.dataConsulta()),
                ()-> assertEquals(agendamento.getStatus(),output.status())
        );

        verify(agendamentoGateway).haConflitoDeAgenda(profissionalId,dataAgendamento);
        verify(pacienteGateway).buscarPorId(pacienteId);
        verify(profissionalGateway).buscarPorId(profissionalId);
        verify(agendamentoGateway).salvar(argumentoCaptor.capture());

        var agendamentoCriado = argumentoCaptor.getValue();

        assertAll(
                ()-> assertNull(agendamentoCriado.getId()),
                ()-> assertEquals(paciente, agendamentoCriado.getPaciente()),
                ()-> assertEquals(profissional, agendamentoCriado.getProfissional()),
                ()-> assertEquals(dataAgendamento, agendamentoCriado.getDataAgendamento()),
                ()-> assertEquals(AGENDADO, agendamentoCriado.getStatus())
        );
        verifyNoMoreInteractions(pacienteGateway, profissionalGateway, agendamentoGateway);
    }

    @Test
    void deveLancarExcecaoQuandoPacienteNaoExiste(){
        var novoAgendamentoInput = AgendamentoFactory.agendamentoInput();
        var idPacienteInexistente = novoAgendamentoInput.pacienteId();
        var idProfissional = novoAgendamentoInput.profissionalId();
        var dataAgendamento = novoAgendamentoInput.dataConsulta();

        when(pacienteGateway.buscarPorId(idPacienteInexistente))
                .thenReturn(Optional.empty());

        assertThrows(
                PacienteNotFoundException.class,
                ()-> criarAgendamento.criar(novoAgendamentoInput)
        );

        verify(agendamentoGateway).haConflitoDeAgenda(idProfissional,dataAgendamento);
        verify(pacienteGateway).buscarPorId(idPacienteInexistente);
        verify(profissionalGateway, never()).buscarPorId(any(Long.class));
        verify(agendamentoGateway, never()).salvar(any(Agendamento.class));
        verifyNoMoreInteractions(pacienteGateway, profissionalGateway, agendamentoGateway);
    }

    @Test
    void deveLancarExcecaoQuandoProfissionalNaoExiste(){
        var novoAgendamentoInput = AgendamentoFactory.agendamentoInput();
        var idPaciente = novoAgendamentoInput.pacienteId();
        var paciente = AgendamentoFactory.paciente();
        var idProfissionalInexistente = novoAgendamentoInput.profissionalId();
        var dataAgendamento = novoAgendamentoInput.dataConsulta();

        when(pacienteGateway.buscarPorId(idPaciente))
                .thenReturn(Optional.of(paciente));
        when(profissionalGateway.buscarPorId(idProfissionalInexistente))
                .thenReturn(Optional.empty());

        assertThrows(
                ProfissionalNotFoundException.class,
                ()-> criarAgendamento.criar(novoAgendamentoInput)
        );

        verify(agendamentoGateway).haConflitoDeAgenda(novoAgendamentoInput.profissionalId(), dataAgendamento);
        verify(pacienteGateway).buscarPorId(idPaciente);
        verify(profissionalGateway).buscarPorId(idProfissionalInexistente);
        verify(agendamentoGateway, never()).salvar(any(Agendamento.class));
        verifyNoMoreInteractions(pacienteGateway, profissionalGateway, agendamentoGateway);
    }

    @Test
    void deveLancarExcecaoQuandoDataAgendamentoPassada(){
        var agendamentoInputDataPassada = AgendamentoFactory.agendamentoInputDataPassada();

        assertThrows(
                DataInvalidaException.class,
                ()->criarAgendamento.criar(agendamentoInputDataPassada)
        );

        verify(agendamentoGateway, never()).haConflitoDeAgenda(agendamentoInputDataPassada.profissionalId(), agendamentoInputDataPassada.dataConsulta());
        verify(pacienteGateway, never()).buscarPorId(any(Long.class));
        verify(profissionalGateway, never()).buscarPorId(any(Long.class));
        verify(agendamentoGateway, never()).salvar(any(Agendamento.class));
        verifyNoMoreInteractions(pacienteGateway, profissionalGateway, agendamentoGateway);
    }

    @Test
    void deveLancarExcecaoQuandoConflitoDeAgenda(){
        var novoAgendamentoInput = AgendamentoFactory.agendamentoInput();
        var idProfissional = novoAgendamentoInput.profissionalId();
        var dataAgendamento = novoAgendamentoInput.dataConsulta();

        when(agendamentoGateway.haConflitoDeAgenda(idProfissional, dataAgendamento))
                .thenReturn(true);

        assertThrows(
                ConflitoAgendamentoException.class,
                ()->criarAgendamento.criar(novoAgendamentoInput)
        );

        verify(agendamentoGateway).haConflitoDeAgenda(idProfissional, dataAgendamento);
        verify(pacienteGateway, never()).buscarPorId(any(Long.class));
        verify(profissionalGateway, never()).buscarPorId(any(Long.class));
        verify(agendamentoGateway, never()).salvar(any(Agendamento.class));
        verifyNoMoreInteractions(pacienteGateway, profissionalGateway, agendamentoGateway);
    }
}

