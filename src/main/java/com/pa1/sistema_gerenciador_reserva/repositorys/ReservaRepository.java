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
    @Query("SELECT r FROM Reserva r JOIN FETCH r.morador u WHERE r.id = :id AND u.roles = :roles")
    Optional<Reserva> findByIdUser(Long id, List<Role> roles);


    @Query("SELECT r FROM Reserva r JOIN FETCH r.morador u WHERE r.data = :data")
    List<Reserva> findByDate(LocalDate data);


    @Query("SELECT COUNT(r) > 0 FROM Reserva r " +
            "WHERE r.local = :Nomelocal " +
            "AND r.data = :data " +
            "AND r.status = :status " +
            "AND r.hora = :hora")
    boolean existeReservaNoMesmoHorario(String Nomelocal, LocalDate data, StatusReserva status, LocalTime hora);

}
