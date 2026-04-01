package com.pa1.sistema_gerenciador_reserva.mapper;

import com.pa1.sistema_gerenciador_reserva.domain.Local;
import com.pa1.sistema_gerenciador_reserva.dto.LocalDTO;
import com.pa1.sistema_gerenciador_reserva.dto.LocalDTOResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LocalMapper extends GenericsMapper<Local, LocalDTO> {
    LocalDTOResponse toDTOResponse(Local local);
    List<LocalDTOResponse> toDTOList(List<Local> locais);

    void updateEntityFromDto(LocalDTO dto, @MappingTarget Local entity);
}

