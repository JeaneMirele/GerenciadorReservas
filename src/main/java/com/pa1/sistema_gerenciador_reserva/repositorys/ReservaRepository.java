package com.pa1.sistema_gerenciador_reserva.repositorys;

import com.pa1.sistema_gerenciador_reserva.domain.Reserva;
import com.pa1.sistema_gerenciador_reserva.domain.Role;
import com.pa1.sistema_gerenciador_reserva.domain.StatusReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    @Query("SELECT r FROM Reserva r JOIN FETCH r.morador WHERE r.data = :data")
    List<Reserva> findByDate(LocalDate data);


    @Query("SELECT COUNT(r) > 0 FROM Reserva r " +
            "WHERE r.local.id = : id_local " +
            "AND r.data = :data " +
            "AND r.status = :status " +
            "AND r.hora = :hora " +
            "AND (:id IS NULL OR r.id != :id)")
    boolean existeReservaNoMesmoHorario(Long id_local, LocalDate data, StatusReserva status, LocalTime hora, Long id);

}
