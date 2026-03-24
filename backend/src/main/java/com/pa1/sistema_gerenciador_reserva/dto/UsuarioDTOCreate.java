package com.pa1.sistema_gerenciador_reserva.dto;

import com.pa1.sistema_gerenciador_reserva.domain.Endereco;
import com.pa1.sistema_gerenciador_reserva.domain.Unidade;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

@Data
public class UsuarioDTOCreate {

    private String nome;

    @Email
    private String email;

    private String senha;

    @CPF
    private String cpf;

    private String telefone;

    private EnderecoDTO endereco;

    private Long unidadeId;

}
