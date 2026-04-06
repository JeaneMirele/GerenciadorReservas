package com.pa1.sistema_gerenciador_reserva.services;

import com.pa1.sistema_gerenciador_reserva.config.SecurityUserValidator;
import com.pa1.sistema_gerenciador_reserva.domain.Usuario;
import com.pa1.sistema_gerenciador_reserva.dto.UsuarioDTO;
import com.pa1.sistema_gerenciador_reserva.dto.UsuarioDTOResponse;
import com.pa1.sistema_gerenciador_reserva.mapper.UsuarioMapper;
import com.pa1.sistema_gerenciador_reserva.repositorys.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;
    private final SecurityUserValidator securityUserValidator;

    @PreAuthorize("hasAnyRole('GERENTE', 'SINDICO')")
    public List<UsuarioDTOResponse> findAll() {
        return usuarioMapper.toDTOList(usuarioRepository.findAll());
    }

    @PreAuthorize("@securityUserValidator.podeAcessarPerfil(authentication, #email)")
    public UsuarioDTOResponse findByEmail(String email) {
        Usuario user = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return usuarioMapper.toDTOResponse(user);
    }

    @Transactional
    @PreAuthorize("@securityUserValidator.podeGerenciar(authentication, #dto.roles())")
    public UsuarioDTOResponse save(UsuarioDTO dto) {
        Usuario user = usuarioMapper.toEntity(dto);
        String senhaProvisoria = UUID.randomUUID().toString().substring(0, 8);

        user.setSenha(passwordEncoder.encode(senhaProvisoria));
        user.setPrecisaTrocarSenha(true);

        return usuarioMapper.toDTOResponse(usuarioRepository.save(user));
    }

    @Transactional
    public UsuarioDTOResponse update(UsuarioDTO dto, Long id) {
        Usuario userExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (!securityUserValidator.podeGerenciar(auth, userExistente.getRoles()) ||
                !securityUserValidator.podeGerenciar(auth, dto.roles())) {
            throw new AccessDeniedException("Acesso negado para alterar este perfil.");
        }

        usuarioMapper.updateEntityFromDto(dto, userExistente);
        return usuarioMapper.toDTOResponse(usuarioRepository.save(userExistente));
    }

    @Transactional
    public void delete(Long id) {
        Usuario user = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (!securityUserValidator.podeGerenciar(auth, user.getRoles())) {
            throw new AccessDeniedException("Acesso negado para excluir este perfil.");
        }

        usuarioRepository.delete(user);
    }
}