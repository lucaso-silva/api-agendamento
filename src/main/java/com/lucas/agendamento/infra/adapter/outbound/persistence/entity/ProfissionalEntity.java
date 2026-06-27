package com.lucas.agendamento.infra.adapter.outbound.persistence.entity;

import com.lucas.agendamento.core.domain.TipoProfissional;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "profissionais")
public class ProfissionalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoProfissional tipo;
}
