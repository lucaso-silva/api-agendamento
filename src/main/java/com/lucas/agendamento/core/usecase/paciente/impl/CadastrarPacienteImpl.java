package com.lucas.agendamento.core.usecase.paciente.impl;

import com.lucas.agendamento.core.domain.Paciente;
import com.lucas.agendamento.core.dto.paciente.NovoPacienteInput;
import com.lucas.agendamento.core.dto.paciente.PacienteOutput;
import com.lucas.agendamento.core.exception.CarteirinhaJaCadastradaException;
import com.lucas.agendamento.core.gateway.PacienteGateway;
import com.lucas.agendamento.core.usecase.paciente.CadastrarPaciente;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
@Transactional
public class CadastrarPacienteImpl implements CadastrarPaciente {
    private final PacienteGateway pacienteGateway;

    @Override
    public PacienteOutput cadastrar(NovoPacienteInput input) {
        if(input.carteirinhaId() != null &&
                pacienteGateway.existeCarteirinhaId(input.carteirinhaId())){
            throw new CarteirinhaJaCadastradaException("Carteirinha id '%s' ja cadastrada".formatted(input.carteirinhaId()));
        }
        var novoPaciente = Paciente.novoPaciente(input.nome(),
                input.telefone(),
                input.carteirinhaId());

        return PacienteOutput.from(pacienteGateway.salvar(novoPaciente));
    }
}
