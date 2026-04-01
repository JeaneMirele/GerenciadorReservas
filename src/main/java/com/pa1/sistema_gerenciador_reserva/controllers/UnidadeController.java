package com.pa1.sistema_gerenciador_reserva.controllers;

import com.pa1.sistema_gerenciador_reserva.dto.UnidadeDTO;
import com.pa1.sistema_gerenciador_reserva.dto.UnidadeDTOResponse;
import com.pa1.sistema_gerenciador_reserva.services.UnidadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/unidade")
public class UnidadeController {
    private final UnidadeService unidadeService;


    @GetMapping
    public ResponseEntity<List<UnidadeDTOResponse>> findAll() {
        return new ResponseEntity<>(unidadeService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnidadeDTOResponse> findById(@PathVariable Long id) {
        return new ResponseEntity<>(unidadeService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UnidadeDTOResponse> create(@RequestBody UnidadeDTO unidadeDTO) {
        UnidadeDTOResponse unidade = unidadeService.save(unidadeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(unidade);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UnidadeDTOResponse> update(@RequestBody UnidadeDTO unidadeDTO, @PathVariable Long id) {
        UnidadeDTOResponse unidade = unidadeService.update(unidadeDTO, id);
        return ResponseEntity.status(HttpStatus.OK).body(unidade);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        unidadeService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}




