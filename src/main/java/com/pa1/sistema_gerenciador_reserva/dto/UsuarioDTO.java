package com.pa1.sistema_gerenciador_reserva.dto;

import com.pa1.sistema_gerenciador_reserva.domain.Role;
import jakarta.validation.constraints.Email;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;
import java.util.Set;

public record UsuarioDTO(
        String nome,
        @Email String email,
        String senha,
        @CPF String cpf,
        String telefone,
        EnderecoDTO endereco,
        UnidadeDTO unidade,
        Set<Role> roles
) {
}
