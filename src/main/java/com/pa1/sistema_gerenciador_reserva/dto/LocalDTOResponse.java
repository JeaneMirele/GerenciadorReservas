package com.pa1.sistema_gerenciador_reserva.dto;


import java.time.Duration;
import java.time.LocalTime;

public record LocalDTOResponse(
    Long id,
    String nome,
    Integer capacidade,
    Duration duracao,
    Double taxaReserva,
    LocalTime horarioInicio,
    LocalTime horarioFim,
    String localizacao
){}