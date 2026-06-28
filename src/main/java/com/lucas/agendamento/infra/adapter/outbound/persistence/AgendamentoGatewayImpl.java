package com.lucas.agendamento.infra.adapter.outbound.persistence;

import com.lucas.agendamento.core.domain.Agendamento;
import com.lucas.agendamento.core.usecase.agendamento.FiltroBusca;
import com.lucas.agendamento.core.gateway.AgendamentoGateway;
import com.lucas.agendamento.infra.adapter.outbound.persistence.mapper.AgendamentoMapper;
import com.lucas.agendamento.infra.adapter.outbound.persistence.repository.AgendamentoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.lucas.agendamento.core.domain.StatusAgendamento.AGENDADO;

@Repository
@AllArgsConstructor
public class AgendamentoGatewayImpl implements AgendamentoGateway {
    private final AgendamentoRepository agendamentoRepository;

    @Override
    public Agendamento salvar(Agendamento agendamento) {
        var entity = agendamentoRepository.save(AgendamentoMapper.toEntity(agendamento));
        return AgendamentoMapper.toDomain(entity);
    }

    @Override
    public List<Agendamento> listar(FiltroBusca filtro) {
        var paciente = filtro.paciente() == null ? null : "%"+filtro.paciente().trim().toLowerCase()+"%";
        var profissional = filtro.profissional() == null ? null : "%"+filtro.profissional().trim().toLowerCase()+"%";

        return agendamentoRepository.buscar(paciente,profissional,filtro.status())
                .stream()
                .map(AgendamentoMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Agendamento> buscarPorId(Long id) {
        return agendamentoRepository.findById(id)
                .map(AgendamentoMapper::toDomain);
    }

    @Override
    public boolean haConflitoDeAgenda(Long profissionalId, LocalDateTime dataAgendamento) {
        return agendamentoRepository.existsByProfissional_IdAndDataAgendamentoAndStatus(profissionalId,dataAgendamento,AGENDADO);
    }
}
