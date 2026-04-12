package com.pa1.sistema_gerenciador_reserva.controllers;

import com.pa1.sistema_gerenciador_reserva.domain.Usuario;
import com.pa1.sistema_gerenciador_reserva.dto.CadastroDTOResponse;
import com.pa1.sistema_gerenciador_reserva.dto.UsuarioDTOResponse;
import com.pa1.sistema_gerenciador_reserva.dto.UsuarioDTO;
import com.pa1.sistema_gerenciador_reserva.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioDTOResponse> findByEmail(@PathVariable String email){
        return new ResponseEntity<>(usuarioService.findByEmail(email), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CadastroDTOResponse> create (@RequestBody @Valid UsuarioDTO usuario){
        CadastroDTOResponse savedUsuario = usuarioService.save(usuario);
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

    @PatchMapping("/meu-perfil/foto")
    public ResponseEntity<UsuarioDTOResponse> atualizarMinhaFoto(
            @RequestParam("arquivo") MultipartFile arquivo) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        UsuarioDTOResponse atualizado = usuarioService.atualizarFoto(email, arquivo);
        return ResponseEntity.ok(atualizado);
    }


}
