package com.pa1.sistema_gerenciador_reserva.repositorys;

import com.pa1.sistema_gerenciador_reserva.domain.Local;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalRepository extends JpaRepository<Local, Long> {
}
