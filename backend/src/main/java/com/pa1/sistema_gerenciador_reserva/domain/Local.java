package com.pa1.sistema_gerenciador_reserva.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Local {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Preencha o nome do local!")
    private String nome;

    @NotNull(message = "Preencha a capacidade do local")
    private Integer capacidade;

    @NotBlank
    private Duration duracao;

    private Double taxaReserva;

    private LocalTime horarioInicio;

    private LocalTime horarioFim;
}
