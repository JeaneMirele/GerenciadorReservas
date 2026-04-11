package com.pa1.sistema_gerenciador_reserva.mapper;

import com.pa1.sistema_gerenciador_reserva.domain.Unidade;
import com.pa1.sistema_gerenciador_reserva.dto.UnidadeDTO;
import com.pa1.sistema_gerenciador_reserva.dto.UnidadeDTOResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UnidadeMapper extends GenericsMapper<Unidade, UnidadeDTO> {
    UnidadeDTOResponse toDTOResponse(Unidade unidade);

    List<UnidadeDTOResponse> toDTOList(List<Unidade> unidades);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UnidadeDTO dto, @MappingTarget Unidade entity);
}
