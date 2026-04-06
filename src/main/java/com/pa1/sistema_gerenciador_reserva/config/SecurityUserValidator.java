package com.pa1.sistema_gerenciador_reserva.config;


import com.pa1.sistema_gerenciador_reserva.domain.Role;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component("securityUserValidator")
public class SecurityUserValidator {

    public boolean podeGerenciar(Authentication auth, Set<Role> rolesAlvo) {
        boolean isGerente = temRole(auth, "GERENTE");
        boolean isSindico = temRole(auth, "SINDICO");

        boolean requerGerente = rolesAlvo.contains(Role.MORADOR);
        boolean requerSindico = rolesAlvo.contains(Role.GERENTE) || rolesAlvo.contains(Role.SINDICO);

        if (requerSindico) {
            return isSindico;
        }

        if (requerGerente) {
            return isGerente;
        }

        return false;
    }

    public boolean podeAcessarPerfil(Authentication auth, String emailAlvo) {
        if (temRole(auth, "GERENTE") || temRole(auth, "SINDICO")) {
            return true;
        }
        return auth.getName().equals(emailAlvo);
    }

    private boolean temRole(Authentication auth, String role) {
        return auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(role));
    }
}
