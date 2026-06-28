package com.lucas.agendamento.core.gateway;

import com.lucas.agendamento.core.domain.Agendamento;
import com.lucas.agendamento.core.usecase.agendamento.FiltroBusca;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AgendamentoGateway {

    Agendamento salvar(Agendamento agendamento);

    List<Agendamento> listar(FiltroBusca filtro);

    Optional<Agendamento> buscarPorId(Long id);

    boolean haConflitoDeAgenda(Long profissionalId, LocalDateTime dataAgendamento);
}
