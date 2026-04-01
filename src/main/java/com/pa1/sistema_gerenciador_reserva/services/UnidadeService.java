package com.pa1.sistema_gerenciador_reserva.services;

import com.pa1.sistema_gerenciador_reserva.domain.Unidade;
import com.pa1.sistema_gerenciador_reserva.dto.UnidadeDTO;
import com.pa1.sistema_gerenciador_reserva.dto.UnidadeDTOResponse;
import com.pa1.sistema_gerenciador_reserva.mapper.UnidadeMapper;
import com.pa1.sistema_gerenciador_reserva.repositorys.UnidadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UnidadeService {
    private final UnidadeRepository unidadeRepository;
    private final UnidadeMapper unidadeMapper;

    public List<UnidadeDTOResponse> findAll() {
        List<Unidade> unidades = unidadeRepository.findAll();
        return unidadeMapper.toDTOList(unidades);
    }

    public UnidadeDTOResponse findById(Long id) {
        return unidadeRepository.findById(id)
                .map(unidadeMapper::toDTOResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unidade não encontrada"));
    }

    @Transactional
    public UnidadeDTOResponse save(UnidadeDTO unidadeDTO) {
        Unidade unidade = unidadeMapper.toEntity(unidadeDTO);
        return unidadeMapper.toDTOResponse(unidadeRepository.save(unidade));
    }

    @Transactional
    public UnidadeDTOResponse update(UnidadeDTO unidadeDTO, Long id) {
        Unidade unidade = unidadeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unidade não encontrada"));

        unidadeMapper.updateEntityFromDto(unidadeDTO, unidade);

        return unidadeMapper.toDTOResponse(unidadeRepository.save(unidade));
    }

    @Transactional
    public void delete(Long id) {
        Unidade unidade = unidadeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unidade não encontrada"));
        unidadeRepository.delete(unidade);
    }
}

