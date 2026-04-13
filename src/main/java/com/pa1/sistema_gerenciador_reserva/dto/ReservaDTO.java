package com.pa1.sistema_gerenciador_reserva.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservaDTO {
    private Long id_local;
    private LocalTime horaEntrada;
    private LocalTime horaSaida;
    private LocalDate data;
    private Long id_morador;
}