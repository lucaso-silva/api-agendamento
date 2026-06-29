package com.lucas.agendamento.infra.adapter.inbound.rest.controller;

import com.lucas.agendamento.core.domain.StatusAgendamento;
import com.lucas.agendamento.core.dto.agendamento.AgendamentoOutput;
import com.lucas.agendamento.core.dto.agendamento.MotivoCancelamentoInput;
import com.lucas.agendamento.core.dto.agendamento.NovoAgendamentoInput;
import com.lucas.agendamento.core.usecase.agendamento.CancelarAgendamento;
import com.lucas.agendamento.core.usecase.agendamento.CriarAgendamento;
import com.lucas.agendamento.core.usecase.agendamento.FiltroBusca;
import com.lucas.agendamento.core.usecase.agendamento.ListarAgendamentos;
import com.lucas.agendamento.infra.adapter.inbound.rest.api.AgendamentoApi;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
public class AgendamentoController implements AgendamentoApi {
    private final CriarAgendamento criarAgendamento;
    private final ListarAgendamentos listarAgendamentos;
    private final CancelarAgendamento cancelarAgendamento;


    @Override
    public ResponseEntity<AgendamentoOutput> criar(NovoAgendamentoInput input) {
        var output = criarAgendamento.criar(input);
        var uri = URI.create("/api/agendamentos/%s".formatted(output.idAgendamento()));

        return ResponseEntity.created(uri).body(output);
    }

    @Override
    public ResponseEntity<List<AgendamentoOutput>> listar(String paciente, String profissional, String status) {
        var filtro = new FiltroBusca(paciente, profissional, StatusAgendamento.from(status));

        return ResponseEntity.ok(listarAgendamentos.listar(filtro));
    }

    @Override
    public ResponseEntity<AgendamentoOutput> cancelar(Long agendamentoId, MotivoCancelamentoInput motivo) {

        return ResponseEntity.ok(cancelarAgendamento.cancelar(agendamentoId, motivo));
    }
}