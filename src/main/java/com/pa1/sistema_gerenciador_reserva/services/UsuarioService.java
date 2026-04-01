package com.pa1.sistema_gerenciador_reserva.services;


import com.pa1.sistema_gerenciador_reserva.domain.Unidade;
import com.pa1.sistema_gerenciador_reserva.domain.Usuario;
import com.pa1.sistema_gerenciador_reserva.dto.UsuarioDTOResponse;
import com.pa1.sistema_gerenciador_reserva.dto.UsuarioDTO;
import com.pa1.sistema_gerenciador_reserva.mapper.UsuarioMapper;
import com.pa1.sistema_gerenciador_reserva.repositorys.UnidadeRepository;
import com.pa1.sistema_gerenciador_reserva.repositorys.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RequiredArgsConstructor
@Service
public class UsuarioService {
    final UsuarioRepository usuarioRepository;
    final UnidadeRepository unidadeRepository;
    final UsuarioMapper usuarioMapper;


    public List<UsuarioDTOResponse> findAll() {
        List<Usuario> usuarios = usuarioRepository.findAll();
            return usuarioMapper.toDTOList(usuarios);
    }


    public UsuarioDTOResponse findByEmail(String email) {
      return usuarioRepository.findByEmail(email)
              .map(usuario -> usuarioMapper.toDTOResponse(usuario))
              .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

    }

    @Transactional
    public UsuarioDTOResponse save(UsuarioDTO usuario) {
        Usuario user = usuarioMapper.toEntity(usuario);
        return usuarioMapper.toDTOResponse(usuarioRepository.save(user));
    }

    @Transactional
    public UsuarioDTOResponse update(UsuarioDTO usuarioDTO, Long id) {
        Usuario user = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        usuarioMapper.updateEntityFromDto(usuarioDTO, user);
        return usuarioMapper.toDTOResponse(usuarioRepository.save(user));
    }

    @Transactional
    public void delete(Long id){
     Usuario user = usuarioRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
            usuarioRepository.delete(user);
    }

}
