package com.herysson.menubackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class PedidoRequestDTO {
    private Integer mesa;
    private List<ProdutoIdWrapper> produtos;

    @JsonIgnore
    public List<Long> getProdutosIds() {
        return produtos.stream()
                .map(ProdutoIdWrapper::getId)
                .toList();
    }

    @Data
    public static class ProdutoIdWrapper {
        private Long id;
    }
}