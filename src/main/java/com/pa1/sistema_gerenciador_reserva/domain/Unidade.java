package com.pa1.sistema_gerenciador_reserva.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

@SQLDelete(sql = "UPDATE unidade SET ativo = false WHERE id = ?")
@SQLRestriction("ativo = true")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Unidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String bloco;
    private String apartamento;
    @OneToMany(mappedBy = "unidade")
    private List<Usuario> moradores;
    private Boolean ativo = true;
}
