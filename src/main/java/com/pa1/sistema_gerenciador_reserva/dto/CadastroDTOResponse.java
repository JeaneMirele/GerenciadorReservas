package com.pa1.sistema_gerenciador_reserva.dto;

import com.pa1.sistema_gerenciador_reserva.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


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

    private Set<Role> roles;
}

