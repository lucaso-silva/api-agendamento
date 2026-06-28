package com.lucas.agendamento.core.usecase.agendamento.impl;

import com.lucas.agendamento.core.dto.agendamento.AgendamentoOutput;
import com.lucas.agendamento.core.dto.agendamento.MotivoCancelamentoInput;
import com.lucas.agendamento.core.exception.AgendamentoNotFoundException;
import com.lucas.agendamento.core.gateway.AgendamentoGateway;
import com.lucas.agendamento.core.usecase.agendamento.CancelarAgendamento;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
@Transactional
public class CancelarAgendamentoImpl implements CancelarAgendamento {
    private final AgendamentoGateway agendamentoGateway;

    @Override
    public AgendamentoOutput cancelar(Long agendamentoId, MotivoCancelamentoInput input) {
        var agendamento = agendamentoGateway.buscarPorId(agendamentoId)
                .orElseThrow(()-> new AgendamentoNotFoundException("Agendamento id '%s' nao encontrado".formatted(agendamentoId)));

        agendamento.cancelarAgendamento(input.motivo());

        return AgendamentoOutput.from(agendamentoGateway.salvar(agendamento));
    }
}
