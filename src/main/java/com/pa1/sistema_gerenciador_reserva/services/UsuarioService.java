package com.pa1.sistema_gerenciador_reserva.services;

import com.pa1.sistema_gerenciador_reserva.config.SecurityUserValidator;
import com.pa1.sistema_gerenciador_reserva.domain.Unidade;
import com.pa1.sistema_gerenciador_reserva.domain.Usuario;
import com.pa1.sistema_gerenciador_reserva.dto.*;
import com.pa1.sistema_gerenciador_reserva.mapper.UsuarioMapper;
import com.pa1.sistema_gerenciador_reserva.repositorys.UnidadeRepository;
import com.pa1.sistema_gerenciador_reserva.repositorys.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;
    private final SecurityUserValidator securityUserValidator;
    private final UnidadeRepository unidadeRepository;
    private final FileStorageService fileStorageService;

    @Value("${app.base-url:http://localhost:8080}")
    private String baseUrl;

    @PreAuthorize("hasAnyAuthority('GERENTE', 'SINDICO')")
    public List<UsuarioDTOResponse> findAll() {
        return usuarioMapper.toDTOList(usuarioRepository.findAll());
    }

    @PreAuthorize("@securityUserValidator.podeAcessarPerfil(authentication, #email)")
    public UsuarioDTOResponse findByEmail(String email) {
        Usuario user = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado com o e-mail: " + email));
        return usuarioMapper.toDTOResponse(user, baseUrl);
    }

    @PreAuthorize("hasAnyAuthority('GERENTE', 'SINDICO')")
    public Usuario findById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado com o ID: " + id));
    }
    @Transactional
    @PreAuthorize("@securityUserValidator.podeGerenciar(authentication, #dto.getRoles())")
    public CadastroDTOResponse save(UsuarioDTO dto) {
        Usuario user = usuarioMapper.toEntity(dto);

        if (dto.getId_unidade() != null) {
            Unidade unidade = unidadeRepository.findById(dto.getId_unidade())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foi possível cadastrar: Unidade não encontrada"));
            user.setUnidade(unidade);
        } else {
            user.setUnidade(null);
        }

        String senhaProvisoria = UUID.randomUUID().toString().substring(0, 6);
        user.setSenha(passwordEncoder.encode(senhaProvisoria));
        user.setPrecisaTrocarSenha(true);

        Usuario usuarioSalvo = usuarioRepository.save(user);
        CadastroDTOResponse cadastro = usuarioMapper.toDTOCadastro(usuarioSalvo);
        cadastro.setSenha(senhaProvisoria);
        return cadastro;
    }


    @Transactional
    public UsuarioDTOResponse update(UsuarioDTO dto, Long id) {
        Usuario userExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foi possível atualizar: Usuário não encontrado"));

        usuarioMapper.updateEntityFromDto(dto, userExistente);

        if (dto.getRoles() != null && !dto.getRoles().isEmpty()) {
            userExistente.setRoles(new HashSet<>(dto.getRoles()));
        }

        if (dto.getId_unidade() != null) {
            Unidade unidade = unidadeRepository.findById(dto.getId_unidade()).orElseThrow();
            userExistente.setUnidade(unidade);
        } else {
            userExistente.setUnidade(null);
        }

        return usuarioMapper.toDTOResponse(usuarioRepository.save(userExistente), baseUrl);
    }

    @Transactional
    public void delete(Long id) {
        Usuario user = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foi possível excluir: Usuário não encontrado"));

        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (!securityUserValidator.podeGerenciar(auth, user.getRoles())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para excluir este perfil.");
        }

        usuarioRepository.delete(user);
    }

    @Transactional
    public UsuarioDTOResponse atualizarFoto(String email, MultipartFile arquivo) {
        Usuario user = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado para atualizar foto"));

        fileStorageService.deletarArquivo(user.getFotoPerfil());

        String nomeArquivo = fileStorageService.salvarArquivo(arquivo, "perfil", user.getId().toString());
        user.setFotoPerfil(nomeArquivo);

        Usuario salvo = usuarioRepository.save(user);
        return usuarioMapper.toDTOResponse(salvo, baseUrl);
    }

    @Transactional
    public void alterarSenha(String email, AlterarSenhaDTO dto) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        if (!passwordEncoder.matches(dto.senhaAtual(), usuario.getSenha())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A senha atual está incorreta.");
        }


        if (passwordEncoder.matches(dto.novaSenha(), usuario.getSenha())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A nova senha deve ser diferente da atual.");
        }

        usuario.setSenha(passwordEncoder.encode(dto.novaSenha()));
        usuarioRepository.save(usuario);
    }
}
