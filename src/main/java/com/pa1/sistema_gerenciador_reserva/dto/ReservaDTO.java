package com.pa1.sistema_gerenciador_reserva.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ReservaDTO {
    private Long id_local;
    private LocalTime hora;
    private LocalDate data;
    private Long id_morador;
}