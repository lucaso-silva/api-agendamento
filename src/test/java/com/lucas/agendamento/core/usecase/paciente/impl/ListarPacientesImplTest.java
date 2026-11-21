package com.lucas.agendamento.core.usecase.paciente.impl;

import com.lucas.agendamento.core.domain.Paciente;
import com.lucas.agendamento.core.gateway.PacienteGateway;
import com.lucas.agendamento.factory.PacienteFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListarPacientesImplTest {
    @Mock
    private PacienteGateway pacienteGateway;

    @InjectMocks
    private ListarPacientesImpl listarPacientes;

    @Test
    void deveRetornarUmaListaDePacientes(){
        var listaPacientes = PacienteFactory.listaPacientes();

        when(pacienteGateway.listarPacientes())
                .thenReturn(listaPacientes);

        var listaOutput = listarPacientes.listar();

        assertNotNull(listaOutput);
        assertEquals(2, listaOutput.size());
        assertAll(
                ()-> assertTrue(!listaOutput.isEmpty()),
                ()-> assertEquals(listaPacientes.getFirst().getId(), listaOutput.getFirst().id()),
                ()-> assertEquals(listaPacientes.getFirst().getNome(), listaOutput.getFirst().nome()),
                ()-> assertEquals(listaPacientes.getLast().getId(), listaOutput.getLast().id()),
                ()-> assertEquals(listaPacientes.getLast().getNome(), listaOutput.getLast().nome())
        );

        verify(pacienteGateway).listarPacientes();
        verifyNoMoreInteractions(pacienteGateway);
    }

    @Test
    void deveRetornarUmaListaVaziaQuandoNaoHaPacientesCadastrados(){
        when(pacienteGateway.listarPacientes())
                .thenReturn(List.of());

        var listaOutput = listarPacientes.listar();

        assertNotNull(listaOutput);
        assertTrue(listaOutput.isEmpty());

        verify(pacienteGateway).listarPacientes();
        verifyNoMoreInteractions(pacienteGateway);
    }
}
