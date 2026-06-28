package com.lucas.agendamento.infra.adapter.inbound.rest;

import com.lucas.agendamento.core.dto.paciente.NovoPacienteInput;
import com.lucas.agendamento.core.dto.paciente.PacienteOutput;
import com.lucas.agendamento.core.usecase.paciente.CadastrarPaciente;
import com.lucas.agendamento.core.usecase.paciente.ListarPacientes;
import com.lucas.agendamento.infra.adapter.inbound.rest.api.PacienteApi;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
public class PacienteController implements PacienteApi {
    private final CadastrarPaciente cadastrarPaciente;
    private final ListarPacientes listarPacientes;

    @Override
    public ResponseEntity<PacienteOutput> cadastrar(NovoPacienteInput input) {
        var output = cadastrarPaciente.cadastrar(input);
        var uri = URI.create("/api/pacientes/%s".formatted(output.id()));

        return ResponseEntity.created(uri).body(output);
    }

    @Override
    public ResponseEntity<List<PacienteOutput>> listar() {
        return ResponseEntity.ok(listarPacientes.listar());
    }
}
