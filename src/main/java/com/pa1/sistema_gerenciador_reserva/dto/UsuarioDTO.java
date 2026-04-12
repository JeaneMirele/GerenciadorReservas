package com.pa1.sistema_gerenciador_reserva.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pa1.sistema_gerenciador_reserva.domain.Role;
import jakarta.validation.constraints.*;

import org.hibernate.validator.constraints.br.CPF;
import java.util.Set;

public record UsuarioDTO(
        @NotBlank(message = "Preencha o nome")
        String nome,

        @Email(message = "Insira um e-mail válido")
        @NotBlank(message = "O e-mail é obrigatório")
        String email,

        @CPF String cpf,
        String telefone,

        EnderecoDTOResponse endereco,

        UnidadeDTOResponse unidade,

        @NotNull(message = "Informe o tipo de usuário")
        Set<Role> roles
) {
}
