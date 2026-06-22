package com.herysson.menubackend.dto;

import lombok.Data;

@Data
public class ProdutoRequestDTO {
    private String nome;
    private String descricao;
    private Double preco;
    private String categoria;
    private Boolean disponibilidade;
    private String imagem;
}
