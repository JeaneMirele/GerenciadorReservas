package com.pa1.sistema_gerenciador_reserva.controllers;

import com.pa1.sistema_gerenciador_reserva.dto.UsuarioDTO;
import com.pa1.sistema_gerenciador_reserva.dto.UsuarioDTOCreate;
import com.pa1.sistema_gerenciador_reserva.repositorys.ReservaRepository;
import com.pa1.sistema_gerenciador_reserva.services.UsuarioService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    final UsuarioService usuarioService;
    private final ReservaRepository reservaRepository;

    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioDTO> findByEmail(@PathVariable String email){
        return new ResponseEntity<>(usuarioService.findByEmail(email), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> create (@RequestBody UsuarioDTOCreate usuario){
      return new ResponseEntity<>(usuarioService.save(usuario), HttpStatus.CREATED);
    }

}
