package com.lucas.agendamento.core.usecase.agendamento.impl;

import com.lucas.agendamento.core.domain.Agendamento;
import com.lucas.agendamento.core.dto.agendamento.CriarAgendamentoInput;
import com.lucas.agendamento.core.dto.agendamento.CriarAgendamentoOutput;
import com.lucas.agendamento.core.exception.ConflitoAgendamentoException;
import com.lucas.agendamento.core.exception.DataInvalidaException;
import com.lucas.agendamento.core.exception.PacienteNotFoundException;
import com.lucas.agendamento.core.exception.ProfissionalNotFoundException;
import com.lucas.agendamento.core.gateway.AgendamentoGateway;
import com.lucas.agendamento.core.gateway.PacienteGateway;
import com.lucas.agendamento.core.gateway.ProfissionalGateway;
import com.lucas.agendamento.core.usecase.agendamento.CriarAgendamento;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
@Transactional
public class CriarAgendamentoImpl implements CriarAgendamento {
    private final AgendamentoGateway agendamentoGateway;
    private final PacienteGateway pacienteGateway;
    private final ProfissionalGateway profissionalGateway;

    @Override
    public CriarAgendamentoOutput criar(CriarAgendamentoInput input) {

        validaDataAgendamento(input.profissionalId(), input.dataConsulta());
        var paciente = pacienteGateway.buscarPorId(input.pacienteId())
                        .orElseThrow(()-> new PacienteNotFoundException("Paciente id '%s' nao encontrado".formatted(input.pacienteId())));
        var profissional = profissionalGateway.buscarPorId(input.profissionalId())
                        .orElseThrow(()-> new ProfissionalNotFoundException("Profissional id '%s' nao encontrado.".formatted(input.profissionalId())));

        var novoAgendamento = Agendamento.novoAgendamento(paciente,
                profissional,
                input.dataConsulta(),
                input.tipo());

        return CriarAgendamentoOutput.from(agendamentoGateway.salvar(novoAgendamento));
    }

    private void validaDataAgendamento(Long profissionalId, LocalDateTime dataAgendamento) {
        var dataAtual = LocalDateTime.now();
        if(!dataAgendamento.isAfter(dataAtual)) {
            throw new DataInvalidaException("Data e hora do agendamento devem ser superiores a data e hora atuais");
        }
        if(agendamentoGateway.haConflitoDeAgenda(profissionalId, dataAgendamento)){
            throw new ConflitoAgendamentoException("Profissional ja possui agendamento no mesmo horário");
        }
    }
}
