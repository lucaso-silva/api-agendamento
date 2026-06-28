package com.lucas.agendamento.infra.adapter.inbound.rest;

import com.lucas.agendamento.core.dto.paciente.NovoPacienteInput;
import com.lucas.agendamento.core.dto.paciente.PacienteOutput;
import com.lucas.agendamento.core.usecase.paciente.CadastrarPaciente;
import com.lucas.agendamento.core.usecase.paciente.ListarPacientes;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/pacientes")
public class PacienteController {
    private final CadastrarPaciente cadastrarPaciente;
    private final ListarPacientes listarPacientes;

    @PostMapping
    public ResponseEntity<PacienteOutput> cadastrar(@Valid @RequestBody NovoPacienteInput input){
        var output = cadastrarPaciente.cadastrar(input);
        var uri = URI.create("/api/pacientes/%s".formatted(output.id()));

        return ResponseEntity.created(uri).body(output);
    }

    @GetMapping
    public ResponseEntity<List<PacienteOutput>> listar(){
        return ResponseEntity.ok(listarPacientes.listar());
    }
}
