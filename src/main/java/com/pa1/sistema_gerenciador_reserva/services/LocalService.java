package com.pa1.sistema_gerenciador_reserva.services;


import com.pa1.sistema_gerenciador_reserva.domain.Local;
import com.pa1.sistema_gerenciador_reserva.dto.LocalDTO;
import com.pa1.sistema_gerenciador_reserva.dto.LocalDTOResponse;
import com.pa1.sistema_gerenciador_reserva.mapper.LocalMapper;
import com.pa1.sistema_gerenciador_reserva.repositorys.LocalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
@RequiredArgsConstructor
public class LocalService {

    private final LocalRepository localRepository;
    private final LocalMapper localMapper;

    public List<LocalDTOResponse> findAll() {
        List<Local> locais = localRepository.findAll();
        return localMapper.toDTOList(locais);
    }

    public LocalDTOResponse findById(Long id) {
        return localRepository.findById(id)
                .map(localMapper::toDTOResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Local não encontrado"));
    }

    @Transactional
    public LocalDTOResponse save(LocalDTO localDTO) {
        Local local = localMapper.toEntity(localDTO);
        return localMapper.toDTOResponse(localRepository.save(local));
    }

    @Transactional
    public LocalDTOResponse update(LocalDTO localDTO, Long id) {
        Local local = localRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Local não encontrado"));

        localMapper.updateEntityFromDto(localDTO, local);

        return localMapper.toDTOResponse(localRepository.save(local));
    }

    @Transactional
    public void delete(Long id) {
        Local local = localRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Local não encontrado"));
        localRepository.delete(local);
    }
}
