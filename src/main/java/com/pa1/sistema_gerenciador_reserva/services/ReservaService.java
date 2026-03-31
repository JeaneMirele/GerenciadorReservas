package com.pa1.sistema_gerenciador_reserva.services;

import com.pa1.sistema_gerenciador_reserva.domain.Reserva;
import com.pa1.sistema_gerenciador_reserva.domain.StatusReserva;
import com.pa1.sistema_gerenciador_reserva.dto.ReservaDTO;
import com.pa1.sistema_gerenciador_reserva.dto.ReservaDTOResponse;
import com.pa1.sistema_gerenciador_reserva.mapper.ReservaMapper;
import com.pa1.sistema_gerenciador_reserva.repositorys.ReservaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReservaService {
    private final ReservaRepository reservaRepository;
    private final ReservaMapper reservaMapper;

    public List<ReservaDTOResponse> findAll() {
        return reservaMapper.toDTOList(reservaRepository.findAll());
    }

    public List<ReservaDTOResponse> findByDate(LocalDate data) {
        return reservaMapper.toDTOList(reservaRepository.findByDate(data));
    }

    public ReservaDTOResponse findById(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return reservaMapper.toDTO(reserva);
    }

    @Transactional
    public ReservaDTOResponse save(ReservaDTO reservaDTO) {
        Reserva reserva = reservaRepository.save(reservaMapper.toEntity(reservaDTO));
        reserva.setStatus(StatusReserva.PENDENTE);
        return reservaMapper.toDTO(reserva);
    }

    @Transactional
    public ReservaDTOResponse update(ReservaDTO reservaDTO) {
        Reserva reserva = reservaMapper.toEntity(reservaDTO);
        reservaRepository.findById(reserva.getId())
                .orElseThrow(() -> new EntityNotFoundException("Reserva não encontrada"));

        LocalDate hoje = LocalDate.now();
        LocalDate dataDaReserva = reserva.getData();

        if (hoje.isAfter(dataDaReserva.minusDays(7))) {
            throw new RuntimeException("Alterações só são permitidas com no mínimo 7 dias de antecedência.");
        }
        reserva.setStatus(StatusReserva.PENDENTE);
        return reservaMapper.toDTO(reservaRepository.save(reserva));
    }

    @Transactional
    public void cancelar(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reserva não encontrada"));

        LocalDate hoje = LocalDate.now();
        LocalDate dataDaReserva = reserva.getData();

        if (hoje.isAfter(dataDaReserva.minusDays(14))) {
            throw new RuntimeException("Cancelamentos só são permitidos com no mínimo 14 dias de antecedência.");
        }

        if (reserva.getStatus() == StatusReserva.CANCELADA) {
            throw new RuntimeException("Esta reserva já está cancelada.");
        }
        reserva.setStatus(StatusReserva.CANCELADA);
    }
}