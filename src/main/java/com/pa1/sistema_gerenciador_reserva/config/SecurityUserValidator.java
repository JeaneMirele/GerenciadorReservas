package com.pa1.sistema_gerenciador_reserva.config;


import com.pa1.sistema_gerenciador_reserva.domain.Role;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component("securityUserValidator")
public class SecurityUserValidator {

    public boolean podeGerenciar(Authentication auth, Set<Role> rolesAlvo) {
        if (rolesAlvo == null || rolesAlvo.isEmpty()) {
            return true;
        }

        boolean isSindico = temRole(auth, "SINDICO");
        boolean isGerente = temRole(auth, "GERENTE");

        if (rolesAlvo.contains(Role.SINDICO) || rolesAlvo.contains(Role.GERENTE)) {
            return isSindico;
        }

        if (rolesAlvo.contains(Role.MORADOR)) {
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
