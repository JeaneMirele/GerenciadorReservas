package com.pa1.sistema_gerenciador_reserva.repositorys;

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

    @Query("SELECT r FROM Reserva r JOIN FETCH r.morador WHERE r.data = :data")
    Optional<List<Reserva>> findByDate(LocalDate data);

    @Query("SELECT COUNT(r) > 0 FROM Reserva r " +
            "WHERE r.local.id = :id_local " +
            "AND r.data = :data " +
            "AND r.status != :status " +
            "AND (:id IS NULL OR r.id != :id) " +
            "AND (:horaEntrada < r.horaSaida AND :horaSaida > r.horaEntrada)")
    boolean existeReservaNoMesmoHorario(
            @Param("id_local") Long id_local,
            @Param("data") LocalDate data,
            @Param("status") StatusReserva status,
            @Param("horaEntrada") LocalTime horaEntrada,
            @Param("horaSaida") LocalTime horaSaida,
            @Param("id") Long id
    );
}
