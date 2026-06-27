package com.lucas.agendamento.infra.adapter.outbound.persistence.entity;

import com.lucas.agendamento.core.domain.StatusAgendamento;
import com.lucas.agendamento.core.domain.TipoAtendimento;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "agendamentos")
public class AgendamentoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private PacienteEntity paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_id", nullable = false)
    private ProfissionalEntity profissional;

    @Column(nullable = false, name = "data_agendamento")
    private LocalDateTime dataAgendamento;

    @Column(nullable = false, name = "tipo_atendimento")
    @Enumerated(EnumType.STRING)
    private TipoAtendimento tipo;

    @Column(nullable = false, name = "status")
    @Enumerated(EnumType.STRING)
    private StatusAgendamento status;

    @Column(name = "motivo_cancelamento")
    private String motivoCancelamento;
}
