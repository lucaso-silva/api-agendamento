package com.lucas.agendamento.core.domain;

import com.lucas.agendamento.core.domain.exception.RequiredFieldException;
import lombok.Getter;

@Getter
public class Paciente {
    private Long id;
    private String nome;
    private String telefone;
    private String carteirinhaId;

    private Paciente(String nome, String telefone, String carteirinhaId) {
        this.id = null;
        setNome(nome);
        setTelefone(telefone);
        setCarteirinhaId(carteirinhaId);
    }

    private Paciente(Long id, String nome, String telefone, String carteirinhaId){
        if(id == null || id < 1 ){
            throw new RequiredFieldException("id deve ser informado");
        }
        this.id = id;
        setNome(nome);
        setTelefone(telefone);
        setCarteirinhaId(carteirinhaId);
    }

    public static Paciente novoPaciente(String nome, String telefone, String carteirinhaId){
        return new Paciente(nome, telefone, carteirinhaId);
    }

    public static Paciente restore(Long id, String nome, String telefone, String carteirinhaId){
        return new Paciente(id, nome, telefone, carteirinhaId);
    }

    private void setNome(String nome) {
        if(nome == null || nome.isBlank()){
            throw new RequiredFieldException("Nome deve ser informado");
        }
        this.nome = nome;
    }

    private void setTelefone(String telefone) {
        if(telefone == null || telefone.isBlank()){
            throw new RequiredFieldException("Telefone deve ser informado");
        }
        this.telefone = telefone;
    }

    private void setCarteirinhaId(String carteirinhaId) {
        this.carteirinhaId = carteirinhaId;
    }
}
