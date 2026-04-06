package com.pa1.sistema_gerenciador_reserva.dto;

import jakarta.validation.constraints.NotBlank;

public record PrimeiroAcessoDTO(@NotBlank String email,
                                @NotBlank String senhaProvisoria,
                                @NotBlank String novaSenha
) {}