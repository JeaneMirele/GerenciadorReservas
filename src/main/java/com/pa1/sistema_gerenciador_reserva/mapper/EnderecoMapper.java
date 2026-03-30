package com.pa1.sistema_gerenciador_reserva.mapper;

import com.pa1.sistema_gerenciador_reserva.domain.Endereco;
import com.pa1.sistema_gerenciador_reserva.dto.EnderecoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnderecoMapper extends GenericsMapper<Endereco, EnderecoDTO> {}
