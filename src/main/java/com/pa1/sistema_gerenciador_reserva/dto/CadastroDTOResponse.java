package com.pa1.sistema_gerenciador_reserva.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CadastroDTOResponse {

    private Long id;

    private String nome;

    private String email;

    private String senha;

    private String cpf;

    private String telefone;

    private Boolean precisaTrocarSenha;
}

