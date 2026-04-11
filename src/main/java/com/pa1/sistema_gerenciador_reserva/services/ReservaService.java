package com.pa1.sistema_gerenciador_reserva.services;

import com.pa1.sistema_gerenciador_reserva.domain.Local;
import com.pa1.sistema_gerenciador_reserva.domain.Reserva;
import com.pa1.sistema_gerenciador_reserva.domain.StatusReserva;
import com.pa1.sistema_gerenciador_reserva.domain.Usuario;
import com.pa1.sistema_gerenciador_reserva.dto.ReservaDTO;
import com.pa1.sistema_gerenciador_reserva.dto.ReservaDTOResponse;
import com.pa1.sistema_gerenciador_reserva.mapper.ReservaMapper;
import com.pa1.sistema_gerenciador_reserva.repositorys.LocalRepository;
import com.pa1.sistema_gerenciador_reserva.repositorys.ReservaRepository;
import com.pa1.sistema_gerenciador_reserva.repositorys.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReservaService {
    private final ReservaRepository reservaRepository;
    private final ReservaMapper reservaMapper;
    private final UsuarioRepository usuarioRepository;
    private final LocalRepository localRepository;

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
        Local local = localRepository.findById(reservaDTO.getId_local())
                .orElseThrow(() -> new EntityNotFoundException("Local não encontrado"));

        Usuario morador = usuarioRepository.findById(reservaDTO.getId_morador())
                .orElseThrow(() -> new EntityNotFoundException("Morador não encontrado"));

        Reserva reserva = reservaMapper.toEntity(reservaDTO);
        reserva.setLocal(local);
        reserva.setMorador(morador);

        validarDisponibilidade(reserva);
        reserva.setStatus(StatusReserva.APROVADA);

        return reservaMapper.toDTO(reservaRepository.save(reserva));
    }

    @Transactional
    public ReservaDTOResponse update(ReservaDTO reservaDTO, Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reserva não encontrada"));

        LocalDate hoje = LocalDate.now();

        if (hoje.isAfter(reserva.getData().minusDays(7))) {
            throw new RuntimeException("Alterações só são permitidas com no mínimo 7 dias de antecedência.");
        }

        reservaMapper.updateEntityFromDto(reservaDTO, reserva);
        validarDisponibilidade(reserva);

        return reservaMapper.toDTO(reservaRepository.save(reserva));
    }

    @Transactional
    public ReservaDTOResponse cancelar(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reserva não encontrada"));

        LocalDate hoje = LocalDate.now();

        if (hoje.isAfter(reserva.getData().minusDays(14))) {
            throw new RuntimeException("Cancelamentos só são permitidos com no mínimo 14 dias de antecedência.");
        }

        if (reserva.getStatus() == StatusReserva.CANCELADA) {
            throw new RuntimeException("Esta reserva já está cancelada.");
        }
        reserva.setStatus(StatusReserva.CANCELADA);

        return reservaMapper.toDTO(reservaRepository.save(reserva));
    }

    public void validarDisponibilidade(Reserva reserva) {

        Boolean existeReserva = reservaRepository.existeReservaNoMesmoHorario(reserva.getLocal().getId(), reserva.getData(), StatusReserva.APROVADA, reserva.getHora(), reserva.getId());

        if (existeReserva) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe uma reserva para este local neste horário.");
        }
    }
}