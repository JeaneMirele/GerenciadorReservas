package com.pa1.sistema_gerenciador_reserva.mapper;

import com.pa1.sistema_gerenciador_reserva.domain.Unidade;
import com.pa1.sistema_gerenciador_reserva.dto.UnidadeDTO;
import com.pa1.sistema_gerenciador_reserva.dto.UnidadeDTOResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UnidadeMapper extends GenericsMapper<Unidade, UnidadeDTO> {
    UnidadeDTOResponse toDTOResponse(Unidade unidade);

    List<UnidadeDTOResponse> toDTOList(List<Unidade> unidades);

    void updateEntityFromDto(UnidadeDTO dto, @MappingTarget Unidade entity);
}
