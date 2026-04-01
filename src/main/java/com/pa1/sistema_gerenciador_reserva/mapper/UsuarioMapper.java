package com.pa1.sistema_gerenciador_reserva.mapper;

import com.pa1.sistema_gerenciador_reserva.domain.Local;
import com.pa1.sistema_gerenciador_reserva.domain.Usuario;
import com.pa1.sistema_gerenciador_reserva.dto.LocalDTO;
import com.pa1.sistema_gerenciador_reserva.dto.UsuarioDTOResponse;
import com.pa1.sistema_gerenciador_reserva.dto.UsuarioDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper extends GenericsMapper<Usuario, UsuarioDTO> {
    UsuarioDTOResponse toDTOResponse(Usuario usuario);
    List<UsuarioDTOResponse> toDTOList(List<Usuario> usuarios);

    void updateEntityFromDto(UsuarioDTO dto, @MappingTarget Usuario entity);
}
