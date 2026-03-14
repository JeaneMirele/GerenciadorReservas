package com.pa1.sistema_gerenciador_reserva.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Preencha o nome!")
    private String nome;

    @NotBlank(message = "Preencha o email!")
    private String email;

    @NotBlank(message = "Informe a senha!")
    private String senha;

    @NotNull
    private String cpf;

    @NotNull
    private String telefone;

    @ManyToOne(fetch = FetchType.LAZY)
    private Endereco endereco;

    @ManyToOne(fetch = FetchType.LAZY)
    private Unidade unidade;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();
}
