package com.pa1.sistema_gerenciador_reserva.mapper;

import com.pa1.sistema_gerenciador_reserva.domain.Unidade;
import com.pa1.sistema_gerenciador_reserva.dto.UnidadeDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UnidadeMapper extends GenericsMapper<Unidade, UnidadeDTO> {}
