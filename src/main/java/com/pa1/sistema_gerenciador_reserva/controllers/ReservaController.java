package com.pa1.sistema_gerenciador_reserva.controllers;

import com.pa1.sistema_gerenciador_reserva.dto.ReservaDTO;
import com.pa1.sistema_gerenciador_reserva.dto.ReservaDTOResponse;
import com.pa1.sistema_gerenciador_reserva.dto.UsuarioDTO;
import com.pa1.sistema_gerenciador_reserva.dto.UsuarioDTOResponse;
import com.pa1.sistema_gerenciador_reserva.services.ReservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reserva")
@RequiredArgsConstructor
public class ReservaController {
    private final ReservaService reservaService;

    @GetMapping
    public ResponseEntity<List<ReservaDTOResponse>> findAll(){
        return new ResponseEntity<>(reservaService.findAll(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ReservaDTOResponse>> findByDate(@RequestParam LocalDate data) {
        return new ResponseEntity<>(reservaService.findByDate(data), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaDTOResponse> findById(@PathVariable Long id) {
        return new ResponseEntity<>(reservaService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ReservaDTOResponse> create (@RequestBody ReservaDTO reserva){
        ReservaDTOResponse reservaDTOResponse = reservaService.save(reserva);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservaDTOResponse);
    }

    @PutMapping
    public ResponseEntity<ReservaDTOResponse> update (@RequestBody ReservaDTO reserva){
        ReservaDTOResponse reservaDTOResponse = reservaService.update(reserva);
        return ResponseEntity.status(HttpStatus.OK).body(reservaDTOResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelar (@PathVariable Long id){
        reservaService.cancelar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
