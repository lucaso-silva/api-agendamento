package com.lucas.agendamento.core.domain;

import com.lucas.agendamento.core.domain.exception.RequiredFieldException;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Agendamento {
    private Long id;
    private Paciente paciente;
    private Profissional profissional;
    private LocalDateTime dataConsulta;
    private TipoAtendimento tipo;
    private StatusAgendamento status;
    private String motivoCancelamento;

    private Agendamento(Paciente paciente, Profissional profissional, LocalDateTime dataConsulta, TipoAtendimento tipo) {
        if(paciente == null || profissional == null){
            throw new RequiredFieldException("Paciente e profissional devem ser informados");
        }

        this.id = null;
        this.paciente = paciente;
        this.profissional = profissional;
        setDataConsulta(dataConsulta);
        setTipo(tipo);
        this.status = StatusAgendamento.AGENDADO;
        this.motivoCancelamento = null;
    }

    private Agendamento(Long id, Paciente paciente, Profissional profissional, LocalDateTime dataConsulta, TipoAtendimento tipo, StatusAgendamento status, String motivoCancelamento) {
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
        setDataConsulta(dataConsulta);
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

    private void setDataConsulta(LocalDateTime dataConsulta) {
        if(dataConsulta == null){
            throw new RequiredFieldException("Data do agendamento deve ser informado");
        }
        this.dataConsulta = dataConsulta;
    }

    private void setTipo(TipoAtendimento tipo) {
        if(tipo == null){
            throw new RequiredFieldException("Tipo do atendimento deve ser informado");
        }
        this.tipo = tipo;
    }

    private void cancelarConsulta(String motivoCancelamento) {
        if(motivoCancelamento == null || motivoCancelamento.isBlank()){
            throw new RequiredFieldException("Motivo do cancelamento deve ser informado");
        }
        this.status = StatusAgendamento.CANCELADO;
        this.motivoCancelamento = motivoCancelamento;
    }
}
