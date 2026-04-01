package com.pa1.sistema_gerenciador_reserva.controllers;


import com.pa1.sistema_gerenciador_reserva.dto.LocalDTO;
import com.pa1.sistema_gerenciador_reserva.dto.LocalDTOResponse;
import com.pa1.sistema_gerenciador_reserva.dto.UsuarioDTO;
import com.pa1.sistema_gerenciador_reserva.dto.UsuarioDTOResponse;
import com.pa1.sistema_gerenciador_reserva.services.LocalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/local")
@RequiredArgsConstructor
public class LocalController {
    final LocalService localService;

    @GetMapping
    public ResponseEntity<List<LocalDTOResponse>> findAll(){
        return new ResponseEntity<>(localService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocalDTOResponse>findById(@PathVariable Long id){
        return new ResponseEntity<>(localService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<LocalDTOResponse> create (@RequestBody LocalDTO localDTO){
        LocalDTOResponse local = localService.save(localDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(local);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocalDTOResponse> update (@RequestBody LocalDTO localDTO, @PathVariable Long id){
    LocalDTOResponse local = localService.update(localDTO, id);
        return ResponseEntity.status(HttpStatus.OK).body(local);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id){
        localService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
