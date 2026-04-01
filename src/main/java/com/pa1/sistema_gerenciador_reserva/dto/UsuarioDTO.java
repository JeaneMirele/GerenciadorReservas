package com.pa1.sistema_gerenciador_reserva.dto;

import jakarta.validation.constraints.Email;

import org.hibernate.validator.constraints.br.CPF;

public record UsuarioDTO(
        String nome,
        @Email String email,
        String senha,
        @CPF String cpf,
        String telefone,
        EnderecoDTO endereco,
        UnidadeDTO unidade
) {
}
