package com.pa1.sistema_gerenciador_reserva.dto;

import com.pa1.sistema_gerenciador_reserva.domain.Local;
import com.pa1.sistema_gerenciador_reserva.domain.Usuario;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservaDTO(Local local, LocalTime hora, LocalDate data, Usuario morador){
}
