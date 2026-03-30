package com.pa1.sistema_gerenciador_reserva.mapper;


public interface GenericsMapper<E, D> {
        E toEntity(D dto);
        D toDTO(E entity);
}

