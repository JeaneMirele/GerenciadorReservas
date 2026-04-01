package com.pa1.sistema_gerenciador_reserva.controllers;

import com.pa1.sistema_gerenciador_reserva.dto.UsuarioDTOResponse;
import com.pa1.sistema_gerenciador_reserva.dto.UsuarioDTO;
import com.pa1.sistema_gerenciador_reserva.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioDTOResponse>> findAll(){
        return new ResponseEntity<>(usuarioService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{email}")
    public ResponseEntity<UsuarioDTOResponse> findByEmail(@PathVariable String email){
        return new ResponseEntity<>(usuarioService.findByEmail(email), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UsuarioDTOResponse> create (@RequestBody UsuarioDTO usuario){
        UsuarioDTOResponse savedUsuario = usuarioService.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUsuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTOResponse> update (@RequestBody UsuarioDTO usuario, @PathVariable Long id){
        UsuarioDTOResponse savedUsuario = usuarioService.update(usuario, id);
        return ResponseEntity.status(HttpStatus.OK).body(savedUsuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id){
        usuarioService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
