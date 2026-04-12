package com.pa1.sistema_gerenciador_reserva.mapper;

import com.pa1.sistema_gerenciador_reserva.domain.Local;
import com.pa1.sistema_gerenciador_reserva.dto.LocalDTO;
import com.pa1.sistema_gerenciador_reserva.dto.LocalDTOResponse;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LocalMapper extends GenericsMapper<Local, LocalDTO> {

    @Mapping(target = "fotoUrl", expression = "java(montarUrl(local.getFotoUrl(), baseUrl))")
    LocalDTOResponse toDTOResponse(Local local, @Context String baseUrl);

    // Sobrecarga sem baseUrl — mantém o nome cru (não deve ser usada nas respostas ao cliente)
    LocalDTOResponse toDTOResponse(Local local);

    List<LocalDTOResponse> toDTOList(List<Local> locais);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(LocalDTO dto, @MappingTarget Local entity);

    default String montarUrl(String nomeArquivo, String baseUrl) {
        if (nomeArquivo == null || nomeArquivo.isBlank()) return null;
        return baseUrl + "/arquivos/" + nomeArquivo;
    }
}
