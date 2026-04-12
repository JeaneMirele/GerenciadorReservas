package com.pa1.sistema_gerenciador_reserva.services;

import com.pa1.sistema_gerenciador_reserva.domain.Local;
import com.pa1.sistema_gerenciador_reserva.dto.LocalDTO;
import com.pa1.sistema_gerenciador_reserva.dto.LocalDTOResponse;
import com.pa1.sistema_gerenciador_reserva.mapper.LocalMapper;
import com.pa1.sistema_gerenciador_reserva.repositorys.LocalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocalService {

    private final LocalRepository localRepository;
    private final LocalMapper localMapper;
    private final FileStorageService fileStorageService;

    @Value("${app.base-url:http://localhost:8080}")
    private String baseUrl;

    public List<LocalDTOResponse> findAll() {
        return localRepository.findAll().stream()
                .map(local -> localMapper.toDTOResponse(local, baseUrl))
                .toList();
    }

    public LocalDTOResponse findById(Long id) {
        return localRepository.findById(id)
                .map(local -> localMapper.toDTOResponse(local, baseUrl))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Local não encontrado"));
    }

    @PreAuthorize("hasAuthority('SINDICO')")
    @Transactional
    public LocalDTOResponse save(LocalDTO localDTO) {
        Local local = localMapper.toEntity(localDTO);
        return localMapper.toDTOResponse(localRepository.save(local), baseUrl);
    }

    @PreAuthorize("hasAuthority('SINDICO')")
    @Transactional
    public LocalDTOResponse update(LocalDTO localDTO, Long id) {
        Local local = localRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Local não encontrado"));
        localMapper.updateEntityFromDto(localDTO, local);
        return localMapper.toDTOResponse(localRepository.save(local), baseUrl);
    }

    @PreAuthorize("hasAuthority('SINDICO')")
    @Transactional
    public void delete(Long id) {
        Local local = localRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Local não encontrado"));
        localRepository.delete(local);
    }

    @Transactional
    @PreAuthorize("hasAuthority('SINDICO')")
    public LocalDTOResponse atualizarFoto(Long id, MultipartFile arquivo) {
        Local local = localRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        fileStorageService.deletarArquivo(local.getFotoUrl());

        String nomeArquivo = fileStorageService.salvarArquivo(arquivo, "local", id.toString());
        local.setFotoUrl(nomeArquivo);

        return localMapper.toDTOResponse(localRepository.save(local), baseUrl);
    }
}
