package com.pa1.sistema_gerenciador_reserva.mapper;

import com.pa1.sistema_gerenciador_reserva.domain.Endereco;
import com.pa1.sistema_gerenciador_reserva.dto.EnderecoDTO;
import com.pa1.sistema_gerenciador_reserva.dto.EnderecoDTOResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EnderecoMapper extends GenericsMapper<Endereco, EnderecoDTO> {
    EnderecoDTOResponse toDTOResponse(Endereco endereco);

    List<EnderecoDTOResponse> toDTOList(List<Endereco> enderecos);

    void updateEntityFromDto(EnderecoDTO dto, @MappingTarget Endereco entity);
}
