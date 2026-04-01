package com.pa1.sistema_gerenciador_reserva.controllers;

import com.pa1.sistema_gerenciador_reserva.dto.EnderecoDTO;
import com.pa1.sistema_gerenciador_reserva.dto.EnderecoDTOResponse;
import com.pa1.sistema_gerenciador_reserva.services.EnderecoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enderecos")
@RequiredArgsConstructor
public class EnderecoController {

    private final EnderecoService enderecoService;

    @GetMapping
    public ResponseEntity<List<EnderecoDTOResponse>> findAll() {
        return new ResponseEntity<>(enderecoService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnderecoDTOResponse> findById(@PathVariable Long id) {
        return new ResponseEntity<>(enderecoService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EnderecoDTOResponse> create(@RequestBody EnderecoDTO enderecoDTO) {
        EnderecoDTOResponse endereco = enderecoService.save(enderecoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(endereco);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnderecoDTOResponse> update(@RequestBody EnderecoDTO enderecoDTO, @PathVariable Long id) {
        EnderecoDTOResponse endereco = enderecoService.update(enderecoDTO, id);
        return ResponseEntity.status(HttpStatus.OK).body(endereco);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        enderecoService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

