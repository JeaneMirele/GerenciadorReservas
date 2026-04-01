package com.pa1.sistema_gerenciador_reserva.dto;

import java.time.Duration;
import java.time.LocalTime;

public record LocalDTO(String nome,
                       Integer capacidade,
                       Duration duracao,
                       Double taxaReserva,
                       LocalTime horarioInicio,
                       LocalTime horarioFim,
                       String localizacao){

}
