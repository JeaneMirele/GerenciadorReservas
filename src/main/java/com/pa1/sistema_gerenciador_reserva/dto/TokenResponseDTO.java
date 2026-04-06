package com.pa1.sistema_gerenciador_reserva.dto;

public record TokenResponseDTO( String JwtToken,
                                 String refreshToken,
                                 String tipo) {

}
