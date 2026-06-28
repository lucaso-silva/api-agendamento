package com.lucas.agendamento.infra.adapter.outbound.persistence;

import com.lucas.agendamento.core.domain.Profissional;
import com.lucas.agendamento.core.gateway.ProfissionalGateway;
import com.lucas.agendamento.infra.adapter.outbound.persistence.mapper.ProfissionalMapper;
import com.lucas.agendamento.infra.adapter.outbound.persistence.repository.ProfissionalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class ProfissionalGatewayImpl implements ProfissionalGateway {
    private final ProfissionalRepository profissionalRepository;

    @Override
    public Optional<Profissional> buscarPorId(Long id) {

        return profissionalRepository.findById(id)
                .map(ProfissionalMapper::toDomain);
    }
}
