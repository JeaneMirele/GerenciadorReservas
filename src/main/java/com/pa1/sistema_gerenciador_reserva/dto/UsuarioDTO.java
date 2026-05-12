package com.pa1.sistema_gerenciador_reserva.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pa1.sistema_gerenciador_reserva.domain.Role;
import jakarta.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO{
        @NotBlank(message = "Preencha o nome")
        private String nome;

        @Email(message = "Insira um e-mail válido")
        @NotBlank(message = "O e-mail é obrigatório")
        private String email;

        @CPF
        private String cpf;

        private String telefone;

        private EnderecoDTO endereco;

        private Long id_unidade;

        @NotNull(message = "Informe o tipo de usuário")
        private Set<Role> roles;

}
