package com.lucas.agendamento.infra.adapter.inbound.rest;

import com.lucas.agendamento.core.dto.agendamento.AgendamentoOutput;
import com.lucas.agendamento.core.dto.agendamento.NovoAgendamentoInput;
import com.lucas.agendamento.core.usecase.agendamento.CancelarAgendamento;
import com.lucas.agendamento.core.usecase.agendamento.CriarAgendamento;
import com.lucas.agendamento.core.usecase.agendamento.ListarAgendamentos;
import com.lucas.agendamento.core.dto.agendamento.MotivoCancelamentoInput;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/agendamentos")
public class AgendamentoController {
    private final CriarAgendamento criarAgendamento;
    private final ListarAgendamentos listarAgendamentos;
    private final CancelarAgendamento cancelarAgendamento;

    @PostMapping
    public ResponseEntity<AgendamentoOutput> criar(@Valid @RequestBody NovoAgendamentoInput input){
        var output = criarAgendamento.criar(input);
        var uri = URI.create("/api/agendamentos/%s".formatted(output.idAgendamento()));

        return ResponseEntity.created(uri).body(output);
    }

    @GetMapping
    public ResponseEntity<List<AgendamentoOutput>> listar(){
        return ResponseEntity.ok(listarAgendamentos.listar());
    }

    @PatchMapping("/{agendamentoId}/cancelamento")
    public ResponseEntity<AgendamentoOutput> cancelar(@PathVariable Long agendamentoId,
                                                      @Valid @RequestBody MotivoCancelamentoInput motivo){
        return ResponseEntity.ok(cancelarAgendamento.cancelar(agendamentoId, motivo));
    }
}
