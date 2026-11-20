package com.lucas.agendamento.core.usecase.paciente.impl;

import com.lucas.agendamento.core.domain.Paciente;
import com.lucas.agendamento.core.exception.CarteirinhaJaCadastradaException;
import com.lucas.agendamento.core.gateway.PacienteGateway;
import com.lucas.agendamento.factory.PacienteFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CadastrarPacienteImplTest {
    @Mock
    private PacienteGateway pacienteGateway;

    @InjectMocks
    private CadastrarPacienteImpl cadastrarPaciente;

    @Test
    void deveCadastrarUmPacienteComCarteirinha(){
        var novoPacienteInput = PacienteFactory.pacienteInputCarteirinha();
        var paciente = PacienteFactory.pacienteCarteirinha();
        ArgumentCaptor<Paciente> captor = ArgumentCaptor.forClass(Paciente.class);

        when(pacienteGateway.existeCarteirinhaId(novoPacienteInput.carteirinhaId()))
                .thenReturn(false);
        when(pacienteGateway.salvar(any(Paciente.class)))
                .thenReturn(paciente);

        var pacienteOutput = cadastrarPaciente.cadastrar(novoPacienteInput);

        assertNotNull(pacienteOutput);
        assertAll(
                ()-> assertEquals(paciente.getId(), pacienteOutput.id()),
                ()-> assertEquals(paciente.getNome(), pacienteOutput.nome())
        );

        verify(pacienteGateway).existeCarteirinhaId(novoPacienteInput.carteirinhaId());
        verify(pacienteGateway).salvar(captor.capture());

        var novoPaciente = captor.getValue();

        assertAll(
                ()-> assertNull(novoPaciente.getId()),
                ()-> assertEquals(novoPacienteInput.nome(), novoPaciente.getNome()),
                ()-> assertEquals(novoPacienteInput.telefone(),novoPaciente.getTelefone()),
                ()-> assertEquals(novoPacienteInput.carteirinhaId(), novoPaciente.getCarteirinhaId())
        );

        verifyNoMoreInteractions(pacienteGateway);
    }

    @Test
    void deveCadastrarUmPacienteSemCarteirinha() {
        var novoPacienteInput = PacienteFactory.pacienteInputSemCarteirinha();
        var paciente = PacienteFactory.pacienteSemCarteirinha();
        ArgumentCaptor<Paciente> captor = ArgumentCaptor.forClass(Paciente.class);

        when(pacienteGateway.salvar(any(Paciente.class)))
                .thenReturn(paciente);

        var pacienteOutput = cadastrarPaciente.cadastrar(novoPacienteInput);

        assertNotNull(pacienteOutput);
        assertAll(
                ()-> assertEquals(paciente.getId(), pacienteOutput.id()),
                ()-> assertEquals(paciente.getNome(), pacienteOutput.nome())
        );

        verify(pacienteGateway, never()).existeCarteirinhaId(novoPacienteInput.carteirinhaId());
        verify(pacienteGateway).salvar(captor.capture());

        var novoPaciente = captor.getValue();

        assertAll(
                ()-> assertNull(novoPaciente.getId()),
                ()-> assertNull(novoPaciente.getCarteirinhaId()),
                ()-> assertEquals(novoPacienteInput.nome(), novoPaciente.getNome()),
                ()-> assertEquals(novoPacienteInput.telefone(),novoPaciente.getTelefone())
        );

        verifyNoMoreInteractions(pacienteGateway);
    }

    @Test
    void deveLancarExcecaoQuandoCarteirinhaJaCadastrada(){
        var novoPacienteInput = PacienteFactory.pacienteInputCarteirinha();
        var carteirinhaId = novoPacienteInput.carteirinhaId();

        when(pacienteGateway.existeCarteirinhaId(carteirinhaId))
                .thenReturn(true);

        assertThrows(
                CarteirinhaJaCadastradaException.class,
                ()-> cadastrarPaciente.cadastrar(novoPacienteInput)
        );

        verify(pacienteGateway).existeCarteirinhaId(carteirinhaId);
        verify(pacienteGateway, never()).salvar(any(Paciente.class));
        verifyNoMoreInteractions(pacienteGateway);
    }
}
