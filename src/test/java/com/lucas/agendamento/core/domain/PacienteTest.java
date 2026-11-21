package com.lucas.agendamento.core.domain;

import com.lucas.agendamento.core.domain.exception.RequiredFieldException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertThrows;

class PacienteTest {
    private static final String NOME = "Joao da Silva";
    private static final String TELEFONE = "(81) 3333-1234";

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"    "})
    void naoDevePermitirNomeNuloOuVazio(String nomeInvalido){
        assertThrows(
          RequiredFieldException.class,
                ()-> Paciente.novoPaciente(nomeInvalido, TELEFONE, null)
        );
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"    "})
    void naoDevePermitirTelefoneNuloOuVazio(String telefoneInvalido){
        assertThrows(
                RequiredFieldException.class,
                ()-> Paciente.novoPaciente(NOME, telefoneInvalido, null)
        );

    }

    @ParameterizedTest
    @NullSource
    @ValueSource(longs = {-1L, 0})
    void naoDevePermitirIdZeroNegativoOuNulo(Long idInvalido){
        assertThrows(
                RequiredFieldException.class,
                ()-> Paciente.restore(idInvalido, NOME, TELEFONE, null)
        );
    }


}
