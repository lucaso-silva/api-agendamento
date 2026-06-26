package com.lucas.agendamento.core.gateway;

import com.lucas.agendamento.core.domain.Agendamento;

import java.time.LocalDateTime;

public interface AgendamentoGateway {

    Agendamento salvar(Agendamento agendamento);

    boolean haConflitoDeAgenda(Long profissionalId, LocalDateTime dataAgendamento);
}
