package com.pa1.sistema_gerenciador_reserva.dto;

import jakarta.validation.constraints.Email;

import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

@Data
public class UsuarioDTO {

    private String nome;

    @Email
    private String email;

    private String senha;

    @CPF
    private String cpf;

    private String telefone;

    private EnderecoDTO endereco;

    private UnidadeDTO unidade;

}
