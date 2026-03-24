package com.pa1.sistema_gerenciador_reserva.services;

import com.pa1.sistema_gerenciador_reserva.domain.Endereco;
import com.pa1.sistema_gerenciador_reserva.domain.Unidade;
import com.pa1.sistema_gerenciador_reserva.domain.Usuario;
import com.pa1.sistema_gerenciador_reserva.dto.UsuarioDTO;
import com.pa1.sistema_gerenciador_reserva.dto.UsuarioDTOCreate;
import com.pa1.sistema_gerenciador_reserva.mapper.EnderecoMapper;
import com.pa1.sistema_gerenciador_reserva.mapper.UnidadeMapper;
import com.pa1.sistema_gerenciador_reserva.mapper.UsuarioMapper;
import com.pa1.sistema_gerenciador_reserva.repositorys.EnderecoRepository;
import com.pa1.sistema_gerenciador_reserva.repositorys.UnidadeRepository;
import com.pa1.sistema_gerenciador_reserva.repositorys.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class UsuarioService {
    final EnderecoRepository enderecoRepository;
    final UsuarioRepository usuarioRepository;
    final UnidadeRepository unidadeRepository;
    final UsuarioMapper usuarioMapper;
    final EnderecoMapper enderecoMapper;


    public UsuarioDTO findByEmail(String email) {
      return usuarioRepository.findByEmail(email)
              .map(usuario -> usuarioMapper.toDTO(usuario))
              .orElseThrow( () -> new RuntimeException("Usuário não encontrado com o email: " + email));

    }

    @Transactional
    public UsuarioDTO save(UsuarioDTOCreate usuario) {
        Unidade unidade = unidadeRepository.findById(usuario.getUnidadeId())
                .orElseThrow(() -> new RuntimeException("Unidade não encontrada"));
        Endereco endereco = enderecoRepository.save(enderecoMapper.toEntity(usuario.getEndereco()));
        Usuario user = usuarioMapper.toEntity(usuario);
        user.setEndereco(endereco);
        user.setUnidade(unidade);
        return usuarioMapper.toDTO(usuarioRepository.save(user));
    }

    public void delete(Long id){}

    public void update(UsuarioDTO usuarioDTO) {}

}
