package com.pa1.sistema_gerenciador_reserva.dto;

import com.pa1.sistema_gerenciador_reserva.domain.Role;

import java.util.Set;

public record UsuarioDTOResponse(Long id, String nome, String email, String cpf, String telefone, EnderecoDTOResponse endereco, UnidadeDTOResponse unidade, String foto, Set<Role> roles) {
}
