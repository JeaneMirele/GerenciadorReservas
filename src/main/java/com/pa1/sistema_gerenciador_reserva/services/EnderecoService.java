package com.pa1.sistema_gerenciador_reserva.services;

import com.pa1.sistema_gerenciador_reserva.domain.Endereco;
import com.pa1.sistema_gerenciador_reserva.dto.EnderecoDTO;
import com.pa1.sistema_gerenciador_reserva.dto.EnderecoDTOResponse;
import com.pa1.sistema_gerenciador_reserva.mapper.EnderecoMapper;
import com.pa1.sistema_gerenciador_reserva.repositorys.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EnderecoService {
    private final EnderecoRepository enderecoRepository;
    private final EnderecoMapper enderecoMapper;

    public List<EnderecoDTOResponse> findAll() {
        List<Endereco> enderecos = enderecoRepository.findAll();
        return enderecoMapper.toDTOList(enderecos);
    }

    public EnderecoDTOResponse findById(Long id) {
        return enderecoRepository.findById(id)
                .map(enderecoMapper::toDTOResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço não encontrado."));
    }

    @Transactional
    public EnderecoDTOResponse save(EnderecoDTO enderecoDTO) {
        Endereco endereco = enderecoMapper.toEntity(enderecoDTO);
        return enderecoMapper.toDTOResponse(enderecoRepository.save(endereco));
    }

    @Transactional
    public EnderecoDTOResponse update(EnderecoDTO enderecoDTO, Long id) {
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço não encontrado."));

        enderecoMapper.updateEntityFromDto(enderecoDTO, endereco);

        return enderecoMapper.toDTOResponse(enderecoRepository.save(endereco));
    }

    @Transactional
    public void delete(Long id) {
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço não encontrado."));
        enderecoRepository.delete(endereco);
    }
}

