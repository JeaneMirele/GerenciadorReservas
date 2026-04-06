package com.pa1.sistema_gerenciador_reserva.repositorys;

import com.pa1.sistema_gerenciador_reserva.domain.TokenLife;
import com.pa1.sistema_gerenciador_reserva.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface TokenLifeRepository extends JpaRepository<TokenLife, Long> {
    Optional<TokenLife> findByToken(String token);

    Optional<TokenLife> findByUsuario(Usuario usuario);
}
