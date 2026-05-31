package com.pa1.sistema_gerenciador_reserva.repositorys;

import com.pa1.sistema_gerenciador_reserva.domain.Local;
import com.pa1.sistema_gerenciador_reserva.domain.Reserva;
import com.pa1.sistema_gerenciador_reserva.domain.Role;
import com.pa1.sistema_gerenciador_reserva.domain.StatusReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    @Query("SELECT r FROM Reserva r JOIN FETCH r.morador WHERE r.data >= :dataInicio and r.data <= :dataFim ")
    Optional<List<Reserva>> findByDate(LocalDate dataInicio, LocalDate dataFim);

    @Query("SELECT COUNT(r) > 0 FROM Reserva r " +
            "WHERE r.local.id = :id_local " +
            "AND r.data = :data " +
            "AND r.status <> :status " +
            "AND (:id IS NULL OR r.id <> :id) " +
            "AND NOT (r.horaSaida <= :horaEntrada OR r.horaEntrada >= :horaSaida)")
    boolean existeReservaNoMesmoHorario(
            @Param("id_local") Long id_local,
            @Param("data") LocalDate data,
            @Param("status") StatusReserva status,
            @Param("horaEntrada") LocalTime horaEntrada,
            @Param("horaSaida") LocalTime horaSaida,
            @Param("id") Long id
    );


    @Query("SELECT r.horaEntrada, r.horaSaida FROM Reserva r WHERE r.local.id = :idLocal AND r.data = :data and r.status <> 'CANCELADA'")
    List<Object[]> getHorariosOcupados(@Param("idLocal") Long idLocal, @Param("data") LocalDate data);


    @Query("SELECT r FROM Reserva r WHERE r.status = :status ")
    List<Reserva> reservaStatus(StatusReserva status);

    @Query("SELECT r FROM Reserva r JOIN FETCH r.morador m WHERE m.id = :id ")
    List<Reserva> reservaPorMorador(Long id);
}
