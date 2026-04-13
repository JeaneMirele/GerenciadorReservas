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
    private final UsuarioRepository usuarioRepository;
    private final LocalRepository localRepository;

    public List<ReservaDTOResponse> findAll() {
        return reservaMapper.toDTOList(reservaRepository.findAll());
    }

    public List<ReservaDTOResponse> findByDate(LocalDate data) {
        List<Reserva> filtroData = reservaRepository.findByDate(data)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não há reservas realizadas nesta data"));
        return reservaMapper.toDTOList(filtroData);
    }

    public ReservaDTOResponse findById(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva não encontrada"));

        return reservaMapper.toDTO(reserva);
    }

    @Transactional
    public ReservaDTOResponse save(ReservaDTO reservaDTO) {
        Local local = localRepository.findById(reservaDTO.getId_local())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Local não encontrado"));

        Usuario morador = usuarioRepository.findById(reservaDTO.getId_morador())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Morador não encontrado"));

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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva não encontrada"));

        LocalDate hoje = LocalDate.now();

        if (hoje.isAfter(reserva.getData().minusDays(7))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Alterações só são permitidas com no mínimo 7 dias de antecedência.");
        }

        reservaMapper.updateEntityFromDto(reservaDTO, reserva);
        validarDisponibilidade(reserva);

        return reservaMapper.toDTO(reservaRepository.save(reserva));
    }

    @Transactional
    public ReservaDTOResponse cancelar(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva não encontrada"));

        LocalDate hoje = LocalDate.now();

        if (hoje.isAfter(reserva.getData().minusDays(14))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cancelamentos só são permitidos com no mínimo 14 dias de antecedência.");
        }

        if (reserva.getStatus() == StatusReserva.CANCELADA) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Esta reserva já está cancelada.");
        }
        reserva.setStatus(StatusReserva.CANCELADA);

        return reservaMapper.toDTO(reservaRepository.save(reserva));
    }

    public void validarDisponibilidade(Reserva reserva) {
        if (reserva.getHoraEntrada().isAfter(reserva.getHoraSaida()) ||
                reserva.getHoraEntrada().equals(reserva.getHoraSaida())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A hora de entrada deve ser anterior à hora de saída.");
        }

        if (reserva.getHoraEntrada().isBefore(reserva.getLocal().getHorarioInicio()) ||
                reserva.getHoraSaida().isAfter(reserva.getLocal().getHorarioFim())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("O local funciona apenas entre %s e %s.",
                            reserva.getLocal().getHorarioInicio(), reserva.getLocal().getHorarioFim()));
        }

        boolean conflito = reservaRepository.existeReservaNoMesmoHorario(
                reserva.getLocal().getId(),
                reserva.getData(),
                StatusReserva.CANCELADA,
                reserva.getHoraEntrada(),
                reserva.getHoraSaida(),
                reserva.getId()
        );

        if (conflito) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este local já possui um agendamento para este período.");
        }
    }
}