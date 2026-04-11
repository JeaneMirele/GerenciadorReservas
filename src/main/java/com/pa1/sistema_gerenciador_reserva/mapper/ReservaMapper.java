package com.pa1.sistema_gerenciador_reserva.mapper;

import com.pa1.sistema_gerenciador_reserva.domain.Reserva;

import com.pa1.sistema_gerenciador_reserva.domain.Usuario;
import com.pa1.sistema_gerenciador_reserva.dto.ReservaDTO;
import com.pa1.sistema_gerenciador_reserva.dto.ReservaDTOResponse;
import com.pa1.sistema_gerenciador_reserva.dto.UsuarioDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReservaMapper extends GenericsMapper<Reserva, ReservaDTOResponse>{
    Reserva toEntity(ReservaDTO reservaDTO);
    List<ReservaDTOResponse> toDTOList(List<Reserva> reservas);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ReservaDTO dto, @MappingTarget Reserva entity);
}
