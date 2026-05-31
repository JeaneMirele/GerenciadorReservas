package com.pa1.sistema_gerenciador_reserva.controllers;

import com.pa1.sistema_gerenciador_reserva.dto.LocalDTO;
import com.pa1.sistema_gerenciador_reserva.dto.LocalDTOResponse;
import com.pa1.sistema_gerenciador_reserva.services.LocalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/locais")
@RequiredArgsConstructor
public class LocalController {

    final LocalService localService;

    @GetMapping
    public ResponseEntity<List<LocalDTOResponse>> findAll() {
        return new ResponseEntity<>(localService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocalDTOResponse> findById(@PathVariable Long id) {
        return new ResponseEntity<>(localService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<LocalDTOResponse> create(@RequestBody LocalDTO localDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(localService.save(localDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocalDTOResponse> update(@RequestBody LocalDTO localDTO, @PathVariable Long id) {
        return ResponseEntity.ok(localService.update(localDTO, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        localService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/foto")
    public ResponseEntity<LocalDTOResponse> atualizarFotoLocal(
            @PathVariable Long id,
            @RequestParam("arquivo") MultipartFile arquivo) {
        return ResponseEntity.ok(localService.atualizarFoto(id, arquivo));
    }
}
