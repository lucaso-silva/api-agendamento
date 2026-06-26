package com.lucas.agendamento.core.gateway;

import com.lucas.agendamento.core.domain.Agendamento;

import java.time.LocalDateTime;
import java.util.List;

public interface AgendamentoGateway {

    Agendamento salvar(Agendamento agendamento);

    List<Agendamento> listar();

    boolean haConflitoDeAgenda(Long profissionalId, LocalDateTime dataAgendamento);
}
