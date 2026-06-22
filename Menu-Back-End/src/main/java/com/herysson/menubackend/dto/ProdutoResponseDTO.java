package com.herysson.menubackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.herysson.menubackend.model.Produto;

@Data
@AllArgsConstructor
public class ProdutoResponseDTO {
    private Long id;
    private String nome;
    private String descricao;
    private Double preco;
    private String categoria;
    private Boolean disponibilidade;
    private String imagem;

    public ProdutoResponseDTO(Produto produto) {
        this.id = produto.getId();
        this.nome = produto.getNome();
        this.descricao = produto.getDescricao();
        this.preco = produto.getPreco();
        this.categoria = produto.getCategoria();
        this.disponibilidade = produto.getDisponibilidade();
        this.imagem = produto.getImagem();
    }
}
