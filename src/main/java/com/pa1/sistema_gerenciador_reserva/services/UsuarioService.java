package com.pa1.sistema_gerenciador_reserva.services;

import com.pa1.sistema_gerenciador_reserva.config.SecurityUserValidator;
import com.pa1.sistema_gerenciador_reserva.domain.Usuario;
import com.pa1.sistema_gerenciador_reserva.dto.CadastroDTOResponse;
import com.pa1.sistema_gerenciador_reserva.dto.UsuarioDTO;
import com.pa1.sistema_gerenciador_reserva.dto.UsuarioDTOResponse;
import com.pa1.sistema_gerenciador_reserva.mapper.UsuarioMapper;
import com.pa1.sistema_gerenciador_reserva.repositorys.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;
    private final SecurityUserValidator securityUserValidator;

    @PreAuthorize("hasAnyAuthority('GERENTE', 'SINDICO')")
    public List<UsuarioDTOResponse> findAll() {
        return usuarioMapper.toDTOList(usuarioRepository.findAll());
    }

    @PreAuthorize("@securityUserValidator.podeAcessarPerfil(authentication, #email)")
    public UsuarioDTOResponse findByEmail(String email) {
        Usuario user = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return usuarioMapper.toDTOResponse(user);
    }

    @PreAuthorize("hasAnyAuthority('GERENTE', 'SINDICO')")
    public Usuario findById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    }

    @Transactional
    @PreAuthorize("@securityUserValidator.podeGerenciar(authentication, #dto.roles())")
    public CadastroDTOResponse save(UsuarioDTO dto) {
        Usuario user = usuarioMapper.toEntity(dto);
        String senhaProvisoria = UUID.randomUUID().toString().substring(0, 6);

        user.setSenha(passwordEncoder.encode(senhaProvisoria));
        user.setPrecisaTrocarSenha(true);
        Usuario usuarioSalvo = usuarioRepository.save(user);
        CadastroDTOResponse cadastro = usuarioMapper.toDTOCadastro(usuarioSalvo);
        cadastro.setSenha(senhaProvisoria);

        return cadastro;

    }

    @Transactional
    @PreAuthorize("@securityUserValidator.podeGerenciar(authentication, #dto.roles())")
    public UsuarioDTOResponse update(UsuarioDTO dto, Long id) {
        Usuario userExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (dto.roles() != null && !dto.roles().isEmpty()) {
            userExistente.setRoles(new HashSet<>(dto.roles()));
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