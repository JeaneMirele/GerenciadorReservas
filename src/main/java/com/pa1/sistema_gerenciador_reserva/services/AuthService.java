package com.pa1.sistema_gerenciador_reserva.services;

import com.pa1.sistema_gerenciador_reserva.domain.TokenLife;
import com.pa1.sistema_gerenciador_reserva.domain.Usuario;
import com.pa1.sistema_gerenciador_reserva.repositorys.TokenLifeRepository;
import com.pa1.sistema_gerenciador_reserva.repositorys.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final TokenLifeRepository tokenLifeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o e-mail: " + username));
    }

    @Transactional
    public TokenLife criarRefreshToken(Usuario usuario) {
        TokenLife refreshToken = tokenLifeRepository.findByUsuario(usuario)
                .orElse(new TokenLife());

        refreshToken.setUsuario(usuario);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setDataExpiracao(Instant.now().plus(7, ChronoUnit.DAYS));

        return tokenLifeRepository.save(refreshToken);
    }

    public TokenLife verificarExpiracao(TokenLife token) {
        if (token.getDataExpiracao().isBefore(Instant.now())) {
            tokenLifeRepository.delete(token);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Sua sessão expirou. Por favor, faça login novamente.");
        }
        return token;
    }

    public Optional<TokenLife> findByToken(String token) {
        return tokenLifeRepository.findByToken(token);
    }
}