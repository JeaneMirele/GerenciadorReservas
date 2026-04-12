package com.pa1.sistema_gerenciador_reserva.mapper;

import com.pa1.sistema_gerenciador_reserva.domain.Usuario;
import com.pa1.sistema_gerenciador_reserva.dto.CadastroDTOResponse;
import com.pa1.sistema_gerenciador_reserva.dto.UsuarioDTOResponse;
import com.pa1.sistema_gerenciador_reserva.dto.UsuarioDTO;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper extends GenericsMapper<Usuario, UsuarioDTO> {

    @Mapping(target = "foto", expression = "java(montarUrl(usuario.getFotoPerfil(), baseUrl))")
    UsuarioDTOResponse toDTOResponse(Usuario usuario, @Context String baseUrl);

    // Sobrecarga sem baseUrl para usos que não precisam da URL (ex: toDTOCadastro)
    @Mapping(target = "foto", source = "fotoPerfil")
    UsuarioDTOResponse toDTOResponse(Usuario usuario);

    @Mapping(target = "foto", expression = "java(montarUrl(u.getFotoPerfil(), baseUrl))")
    UsuarioDTOResponse toResponseWithUrl(Usuario u, @Context String baseUrl);

    List<UsuarioDTOResponse> toDTOList(List<Usuario> usuarios);

    CadastroDTOResponse toDTOCadastro(Usuario usuario);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UsuarioDTO dto, @MappingTarget Usuario entity);

    default String montarUrl(String nomeArquivo, String baseUrl) {
        if (nomeArquivo == null || nomeArquivo.isBlank()) return null;
        return baseUrl + "/arquivos/" + nomeArquivo;
    }
}
