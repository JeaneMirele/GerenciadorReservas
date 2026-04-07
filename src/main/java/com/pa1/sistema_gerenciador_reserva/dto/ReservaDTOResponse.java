package com.pa1.sistema_gerenciador_reserva.dto;

import com.pa1.sistema_gerenciador_reserva.domain.Local;
import com.pa1.sistema_gerenciador_reserva.domain.StatusReserva;
import com.pa1.sistema_gerenciador_reserva.domain.Usuario;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservaDTOResponse(Long id, LocalDTOResponse local, LocalTime hora, LocalDate data, UsuarioDTOResponse morador, StatusReserva status) {
}
