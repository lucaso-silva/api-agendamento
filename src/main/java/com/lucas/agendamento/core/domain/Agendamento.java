package com.lucas.agendamento.core.domain;

import com.lucas.agendamento.core.domain.exception.AgendamentoCanceladoException;
import com.lucas.agendamento.core.domain.exception.RequiredFieldException;
import lombok.Getter;

import java.time.LocalDateTime;

import static com.lucas.agendamento.core.domain.StatusAgendamento.AGENDADO;
import static com.lucas.agendamento.core.domain.StatusAgendamento.CANCELADO;

@Getter
public class Agendamento {
    private Long id;
    private Paciente paciente;
    private Profissional profissional;
    private LocalDateTime dataAgendamento;
    private TipoAtendimento tipo;
    private StatusAgendamento status;
    private String motivoCancelamento;

    private Agendamento(Paciente paciente, Profissional profissional, LocalDateTime dataAgendamento, TipoAtendimento tipo) {
        if(paciente == null || profissional == null){
            throw new RequiredFieldException("Paciente e profissional devem ser informados");
        }

        this.id = null;
        this.paciente = paciente;
        this.profissional = profissional;
        setDataAgendamento(dataAgendamento);
        setTipo(tipo);
        this.status = AGENDADO;
        this.motivoCancelamento = null;
    }

    private Agendamento(Long id, Paciente paciente, Profissional profissional, LocalDateTime dataAgendamento, TipoAtendimento tipo, StatusAgendamento status, String motivoCancelamento) {
        if(id == null || id < 1){
            throw new RequiredFieldException("id deve ser informado");
        }
        if(paciente == null || profissional == null){
            throw new RequiredFieldException("Paciente e profissional devem ser informados");
        }
        if(status == null){
            throw new RequiredFieldException("Status deve ser informado");
        }
        this.id = id;
        this.paciente = paciente;
        this.profissional = profissional;
        setDataAgendamento(dataAgendamento);
        setTipo(tipo);
        this.status = status;
        this.motivoCancelamento = motivoCancelamento;
    }

    public static Agendamento novoAgendamento(Paciente paciente, Profissional profissional, LocalDateTime dataConsulta, TipoAtendimento tipo) {
        return new Agendamento(paciente, profissional, dataConsulta, tipo);
    }

    public static Agendamento restore(Long id, Paciente paciente, Profissional profissional, LocalDateTime dataConsulta, TipoAtendimento tipo, StatusAgendamento status, String motivoCancelamento) {
        return new Agendamento(id, paciente, profissional, dataConsulta, tipo, status, motivoCancelamento);
    }

    private void setDataAgendamento(LocalDateTime dataAgendamento) {
        if(dataAgendamento == null){
            throw new RequiredFieldException("Data do agendamento deve ser informado");
        }
        this.dataAgendamento = dataAgendamento;
    }

    private void setTipo(TipoAtendimento tipo) {
        if(tipo == null){
            throw new RequiredFieldException("Tipo do atendimento deve ser informado");
        }
        this.tipo = tipo;
    }

    public void cancelarAgendamento(String motivoCancelamento) {
        if(this.status == CANCELADO){
            throw new AgendamentoCanceladoException("Agendamento encontra-se cancelado");
        }
        if(motivoCancelamento == null || motivoCancelamento.isBlank()){
            throw new RequiredFieldException("Motivo do cancelamento deve ser informado");
        }
        this.status = CANCELADO;
        this.motivoCancelamento = motivoCancelamento;
    }
}
