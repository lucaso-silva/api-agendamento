package com.lucas.agendamento.core.domain;

import com.lucas.agendamento.core.domain.exception.RequiredFieldException;
import lombok.Getter;

@Getter
public class Profissional {
    private Long id;
    private String nome;
    private String telefone;
    private TipoProfissional tipo;

    private Profissional(String nome, String telefone, TipoProfissional tipo) {
        this.id = null;
        setNome(nome);
        setTelefone(telefone);
        setTipo(tipo);
    }

    private Profissional(Long id, String nome, String telefone, TipoProfissional tipo) {
        if(id == null || id < 1){
            throw new RequiredFieldException("id deve ser informado");
        }
        this.id = id;
        setNome(nome);
        setTelefone(telefone);
        setTipo(tipo);
    }

    public static Profissional novoProfissional(String nome, String telefone, TipoProfissional tipo) {
        return new Profissional(nome, telefone, tipo);
    }

    public static Profissional restore(Long id, String nome, String telefone, TipoProfissional tipo){
        return new Profissional(id, nome, telefone, tipo);
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

    private void setTipo(TipoProfissional tipo) {
        if(tipo == null){
            throw new RequiredFieldException("Tipo de profissional deve ser informado");
        }
        this.tipo = tipo;
    }
}
