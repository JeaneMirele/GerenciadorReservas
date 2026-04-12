package com.pa1.sistema_gerenciador_reserva.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    private Local local;

    private LocalTime horaEntrada;

    private LocalTime  horaSaida;

    private LocalDate data;

    @OneToOne(fetch = FetchType.EAGER)
    private Usuario morador;

    @Enumerated(EnumType.STRING)
    private StatusReserva status;
}
