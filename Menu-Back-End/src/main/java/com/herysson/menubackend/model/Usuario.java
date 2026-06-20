package com.herysson.menubackend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Integer mesa;
    private String tipo; // "ADM" ou "USUARIO"
    private String senha;
}
