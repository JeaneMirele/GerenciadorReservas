package com.pa1.sistema_gerenciador_reserva.controllers;

import com.pa1.sistema_gerenciador_reserva.domain.TokenLife;
import com.pa1.sistema_gerenciador_reserva.domain.Usuario;
import com.pa1.sistema_gerenciador_reserva.dto.LoginDTO;
import com.pa1.sistema_gerenciador_reserva.dto.PrimeiroAcessoDTO;
import com.pa1.sistema_gerenciador_reserva.dto.TokenResponseDTO;
import com.pa1.sistema_gerenciador_reserva.repositorys.UsuarioRepository;
import com.pa1.sistema_gerenciador_reserva.services.AuthService;
import com.pa1.sistema_gerenciador_reserva.services.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {

    private final AuthenticationManager manager;
    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO dados) {

        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());
        var authentication = manager.authenticate(authenticationToken);

        var usuario = (Usuario) authentication.getPrincipal();

        if (usuario.getPrecisaTrocarSenha()) {
            Map<String, String> resposta = new HashMap<>();
            resposta.put("message", "TROCA_SENHA_OBRIGATORIA");
            resposta.put("email", usuario.getEmail());

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(resposta);
        }

        var jwtToken = jwtService.gerarToken(usuario);
        var tokenLife = authService.criarRefreshToken(usuario);

        return ResponseEntity.ok(new TokenResponseDTO(jwtToken, tokenLife.getToken(), "Bearer"));
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponseDTO> atualizarToken(@RequestBody @Valid TokenResponseDTO request) {
        return authService.findByToken(request.refreshToken())
                .map(authService::verificarExpiracao) // Lança 401 via ResponseStatusException se expirado
                .map(TokenLife::getUsuario)
                .map(usuario -> {
                    String token = jwtService.gerarToken(usuario);
                    return ResponseEntity.ok(new TokenResponseDTO(token, request.refreshToken(), "Bearer"));
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Sessão inválida. Faça login novamente."));
    }

    @PostMapping("/primeiro-acesso")
    public ResponseEntity<?> primeiroAcesso(@RequestBody @Valid PrimeiroAcessoDTO dados) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.senhaProvisoria());
        var authentication = manager.authenticate(authenticationToken);

        var usuario = (Usuario) authentication.getPrincipal();

        if (!usuario.getPrecisaTrocarSenha()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O primeiro acesso já foi realizado para este usuário.");
        }

        usuario.setSenha(passwordEncoder.encode(dados.novaSenha()));
        usuario.setPrecisaTrocarSenha(false);
        usuarioRepository.save(usuario);

        Map<String, String> resposta = new HashMap<>();
        resposta.put("message", "Senha atualizada com sucesso! Por favor, realize o login com sua nova senha.");

        return ResponseEntity.ok(resposta);
    }
}