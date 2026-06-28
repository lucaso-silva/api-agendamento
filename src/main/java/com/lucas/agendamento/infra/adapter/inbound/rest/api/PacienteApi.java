package com.lucas.agendamento.infra.adapter.inbound.rest.api;

import com.lucas.agendamento.core.dto.paciente.NovoPacienteInput;
import com.lucas.agendamento.core.dto.paciente.PacienteOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Tag(name = "Pacientes",
        description = "Operações relacionadas ao cadastro e consulta de pacientes")
@RequestMapping("/api/pacientes")
public interface PacienteApi {

    @Operation(
            summary = "Cadastrar novo paciente",
            description = "Realiza o cadastro de um novo paciente e retorna os dados do registro criado",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados do paciente", required = true)
    )
    @PostMapping
    ResponseEntity<PacienteOutput> cadastrar(@Valid @RequestBody NovoPacienteInput input);

    @Operation(
            summary = "Listar pacientes cadastrados",
            description = "Retorna a lista de todos os pacientes cadastrados"
    )
    @GetMapping
    ResponseEntity<List<PacienteOutput>> listar();
}
