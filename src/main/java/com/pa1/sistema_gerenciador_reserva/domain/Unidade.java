package com.pa1.sistema_gerenciador_reserva.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
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
    @JsonIgnore
    @OneToMany(mappedBy = "unidade")
    private List<Usuario> moradores;
    private Boolean ativo = true;
}
