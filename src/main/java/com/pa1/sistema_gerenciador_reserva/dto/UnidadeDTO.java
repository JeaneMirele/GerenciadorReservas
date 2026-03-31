package com.pa1.sistema_gerenciador_reserva.dto;

import com.pa1.sistema_gerenciador_reserva.domain.Usuario;

import java.util.List;

public record UnidadeDTO(Long id, String bloco, String apartamento, List<Usuario> moradores) {
}
