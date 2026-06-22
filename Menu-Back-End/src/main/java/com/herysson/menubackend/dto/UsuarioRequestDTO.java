package com.herysson.menubackend.dto;

import lombok.Data;

@Data
public class UsuarioRequestDTO {
    private String nome;
    private Integer mesa;
    private String tipo;
    private String senha;
}