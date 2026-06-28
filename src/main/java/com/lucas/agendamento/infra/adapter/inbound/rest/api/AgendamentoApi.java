package com.lucas.agendamento.infra.adapter.inbound.rest.api;

import com.lucas.agendamento.core.dto.agendamento.AgendamentoOutput;
import com.lucas.agendamento.core.dto.agendamento.MotivoCancelamentoInput;
import com.lucas.agendamento.core.dto.agendamento.NovoAgendamentoInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Agendamentos",
        description = "Operações relacionadas ao gerencimento e a consulta de agendamentos")
@RequestMapping("/api/agendamentos")
public interface AgendamentoApi {

    @Operation(
            summary = "Criar novo agendamento",
            description = "Realiza o cadastro de um novo agendamento e retorna os dados do registro criado",
            requestBody =  @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados do agendamento", required = true)
    )
    @PostMapping
    ResponseEntity<AgendamentoOutput> criar(@Valid @RequestBody NovoAgendamentoInput input);

    @Operation(
            summary = """
                  Retorna a lista de agendamentos cadastrados.
                  Permite filtrar os resultados por paciente, profissional e status do agendamento.
                  """,
            description = "Retorna a lista de agendamentos cadastrados. Permite filtrar por nome do paciente, nome do profissional e status do agendamento (agendado, cancelado)"
    )
    @GetMapping
    ResponseEntity<List<AgendamentoOutput>> listar(@Parameter(description = "Nome do paciente.")
                                                   @RequestParam(name="paciente", required = false)String paciente,
                                                   @Parameter(description = "Nome do profissional")
                                                   @RequestParam(name = "profissional", required = false) String profissional,
                                                   @Parameter(description = "Status do agendamento",
                                                   example = "AGENDADO")
                                                   @RequestParam(name = "status", required = false) String status);

    @Operation(
            summary = "Realizar cancelamento de um agendamento",
            description = "Cancela um agendamento existente, registrando o motivo do cancelamento",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Motivo do cancelamento", required = true)
    )
    @PatchMapping("/{agendamentoId}/cancelamento")
    ResponseEntity<AgendamentoOutput> cancelar(@PathVariable Long agendamentoId,
                                               @Valid @RequestBody MotivoCancelamentoInput motivo);
}
