package com.pa1.sistema_gerenciador_reserva.controllers;

import com.pa1.sistema_gerenciador_reserva.domain.StatusReserva;
import com.pa1.sistema_gerenciador_reserva.dto.ReservaDTO;
import com.pa1.sistema_gerenciador_reserva.dto.ReservaDTOResponse;
import com.pa1.sistema_gerenciador_reserva.services.ReservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reservas")
@RequiredArgsConstructor
public class ReservaController {
    private final ReservaService reservaService;

    @GetMapping
    public ResponseEntity<List<ReservaDTOResponse>> findAll(){
        return new ResponseEntity<>(reservaService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/data")
    public ResponseEntity<List<ReservaDTOResponse>> findByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
                                                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        return new ResponseEntity<>(reservaService.findByDate(dataInicio, dataFim), HttpStatus.OK);
    }

    @GetMapping("/{id}/{data}")
    public ResponseEntity<List<?>> getHorariosDisponiveis(@PathVariable Long id, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  LocalDate data){
        return new ResponseEntity<>(reservaService.getHorariosVagos(id,data), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaDTOResponse> findById(@PathVariable Long id) {
        return new ResponseEntity<>(reservaService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ReservaDTOResponse> create (@RequestBody ReservaDTO reserva){
        ReservaDTOResponse reservaSalva = reservaService.save(reserva);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservaSalva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservaDTOResponse> update (@RequestBody ReservaDTO reserva, @PathVariable Long id){
        ReservaDTOResponse reservaAtualizada = reservaService.update(reserva, id);
        return ResponseEntity.status(HttpStatus.OK).body(reservaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReservaDTOResponse> cancelar (@PathVariable Long id){
        ReservaDTOResponse reservaCancelada = reservaService.cancelar(id);
        return ResponseEntity.status(HttpStatus.OK).body(reservaCancelada);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ReservaDTOResponse>> statusReserva(@PathVariable StatusReserva status){
        return new ResponseEntity<>(reservaService.reservaStatus(status), HttpStatus.OK);
    }


    @GetMapping("/morador/{id}")
    public ResponseEntity<List<ReservaDTOResponse>> ReservaPorMorador(@PathVariable Long id){
        return new ResponseEntity<>(reservaService.reservaPorMorador(id), HttpStatus.OK);
    }
}
