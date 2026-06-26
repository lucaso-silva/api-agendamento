package com.lucas.agendamento.core.gateway;

import com.lucas.agendamento.core.domain.Profissional;

import java.util.Optional;

public interface ProfissionalGateway {

    Optional<Profissional> buscarPorId(Long id);

}
