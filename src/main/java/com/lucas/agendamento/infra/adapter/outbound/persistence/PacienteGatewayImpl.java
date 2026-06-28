package com.lucas.agendamento.infra.adapter.outbound.persistence;

import com.lucas.agendamento.core.domain.Paciente;
import com.lucas.agendamento.core.gateway.PacienteGateway;
import com.lucas.agendamento.infra.adapter.outbound.persistence.mapper.PacienteMapper;
import com.lucas.agendamento.infra.adapter.outbound.persistence.repository.PacienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class PacienteGatewayImpl implements PacienteGateway {
    private final PacienteRepository pacienteRepository;

    @Override
    public Paciente salvar(Paciente paciente) {
        var entity = pacienteRepository.save(PacienteMapper.toEntity(paciente));
        return  PacienteMapper.toDomain(entity);
    }

    @Override
    public List<Paciente> listarPacientes() {
        return pacienteRepository.findAll().stream()
                .map(PacienteMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Paciente> buscarPorId(Long id) {

        return pacienteRepository.findById(id)
                .map(PacienteMapper::toDomain);
    }

    @Override
    public boolean existeCarteirinhaId(String carteirinhaId) {
        return pacienteRepository.existsByCarteirinhaId(carteirinhaId);
    }
}
