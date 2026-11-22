package com.lucas.agendamento.core.usecase.agendamento.impl;

import com.lucas.agendamento.core.domain.Agendamento;
import com.lucas.agendamento.core.exception.ConflitoAgendamentoException;
import com.lucas.agendamento.core.exception.DataInvalidaException;
import com.lucas.agendamento.core.exception.PacienteNotFoundException;
import com.lucas.agendamento.core.exception.ProfissionalNotFoundException;
import com.lucas.agendamento.core.gateway.AgendamentoGateway;
import com.lucas.agendamento.core.gateway.PacienteGateway;
import com.lucas.agendamento.core.gateway.ProfissionalGateway;
import com.lucas.agendamento.factory.AgendamentoFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

    private Clock clock;
    private CriarAgendamentoImpl criarAgendamento;

    @BeforeEach
    void setup(){
        Instant fixedInstant = Instant.parse("2026-11-22T10:45:00Z");
        clock = Clock.fixed(fixedInstant, ZoneId.of("UTC"));

        criarAgendamento = new CriarAgendamentoImpl(agendamentoGateway, pacienteGateway, profissionalGateway, clock);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 604800}) //604800s = 7 dias
    void deveCriarAgendamentoQuandoDataFutura(int valor){
        var dataAgendamento = LocalDateTime.now(clock).plusSeconds(valor);
        var novoAgendamentoInput = AgendamentoFactory.agendamentoInput(dataAgendamento);
        var agendamento = AgendamentoFactory.agendamento(dataAgendamento);
        var paciente = AgendamentoFactory.paciente();
        var profissional = AgendamentoFactory.medico();
        var pacienteId = paciente.getId();
        var profissionalId = profissional.getId();

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
        var dataAgendamento = LocalDateTime.now(clock).plusDays(7);
        var novoAgendamentoInput = AgendamentoFactory.agendamentoInput(dataAgendamento);
        var idPacienteInexistente = novoAgendamentoInput.pacienteId();
        var idProfissional = novoAgendamentoInput.profissionalId();

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
        var dataAgendamento = LocalDateTime.now(clock).plusDays(7);
        var novoAgendamentoInput = AgendamentoFactory.agendamentoInput(dataAgendamento);
        var idPaciente = novoAgendamentoInput.pacienteId();
        var paciente = AgendamentoFactory.paciente();
        var idProfissionalInexistente = novoAgendamentoInput.profissionalId();

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

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 86400}) //86400s = 1 dia
    void deveLancarExcecaoQuandoDataAgendamentoPassadaOuIgualDataAtual(int valor){
        var dataAgendamento = LocalDateTime.now(clock).minusSeconds(valor);
        var novoAgendamentoInput = AgendamentoFactory.agendamentoInput(dataAgendamento);

        assertThrows(
                DataInvalidaException.class,
                ()->criarAgendamento.criar(novoAgendamentoInput)
        );

        verify(agendamentoGateway, never()).haConflitoDeAgenda(novoAgendamentoInput.profissionalId(), novoAgendamentoInput.dataConsulta());
        verify(pacienteGateway, never()).buscarPorId(any(Long.class));
        verify(profissionalGateway, never()).buscarPorId(any(Long.class));
        verify(agendamentoGateway, never()).salvar(any(Agendamento.class));
        verifyNoMoreInteractions(pacienteGateway, profissionalGateway, agendamentoGateway);
    }

    @Test
    void deveUsarFusoDoRelogioParaValidarData(){
        var instant = Instant.parse("2026-11-22T10:45:00Z");
        var relogioRecife = Clock.fixed(instant, ZoneId.of("America/Recife")); //hora Recife 07:45

        var casoDeUso = new CriarAgendamentoImpl(agendamentoGateway,
                pacienteGateway,
                profissionalGateway,
                relogioRecife);

        //Recife - 07:45, UTC - 10:45 | Hora agendamento - 8:00 Recife
        var dataAgendamento = LocalDateTime.of(2026,11,22,8,0);
        var agendamentoInput = AgendamentoFactory.agendamentoInput(dataAgendamento);
        var paciente = AgendamentoFactory.paciente();
        var profissional = AgendamentoFactory.medico();
        var agendamento = AgendamentoFactory.agendamento(dataAgendamento);

        when(agendamentoGateway.haConflitoDeAgenda(profissional.getId(), dataAgendamento))
                .thenReturn(false);
        when(pacienteGateway.buscarPorId(agendamentoInput.pacienteId()))
                .thenReturn(Optional.of(paciente));
        when(profissionalGateway.buscarPorId(agendamentoInput.profissionalId()))
                .thenReturn(Optional.of(profissional));
        when(agendamentoGateway.salvar(any(Agendamento.class)))
                .thenReturn(agendamento);

        var resultado = casoDeUso.criar(agendamentoInput);

        assertEquals(dataAgendamento, resultado.dataConsulta());
        verify(agendamentoGateway).salvar(any(Agendamento.class));
    }

    @Test
    void deveRejeitarDataPassadaNoFusoUTC(){
        //Fixed clock in setup - 10:45 UTC | Hora agendamento 08:00 UTC
        var dataAgendamento = LocalDateTime.of(2026,11,22,8,00);
        var agendamentoInput = AgendamentoFactory.agendamentoInput(dataAgendamento);

        assertThrows(
                DataInvalidaException.class,
                ()-> criarAgendamento.criar(agendamentoInput)
        );

        verifyNoInteractions(pacienteGateway, profissionalGateway, agendamentoGateway);
    }

    @Test
    void deveLancarExcecaoQuandoConflitoDeAgenda() {
        var dataAgendamento = LocalDateTime.now(clock).plusDays(7);
        var novoAgendamentoInput = AgendamentoFactory.agendamentoInput(dataAgendamento);
        var idProfissional = novoAgendamentoInput.profissionalId();

        when(agendamentoGateway.haConflitoDeAgenda(idProfissional, dataAgendamento))
                .thenReturn(true);

        assertThrows(
                ConflitoAgendamentoException.class,
                () -> criarAgendamento.criar(novoAgendamentoInput)
        );

        verify(agendamentoGateway).haConflitoDeAgenda(idProfissional, dataAgendamento);
        verify(pacienteGateway, never()).buscarPorId(any(Long.class));
        verify(profissionalGateway, never()).buscarPorId(any(Long.class));
        verify(agendamentoGateway, never()).salvar(any(Agendamento.class));
        verifyNoMoreInteractions(pacienteGateway, profissionalGateway, agendamentoGateway);
    }
}

