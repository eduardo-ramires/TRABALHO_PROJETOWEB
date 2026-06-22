package com.herysson.menubackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.herysson.menubackend.model.Pedido;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class PedidoResponseDTO {
    private Long id;
    private Integer mesa;
    private List<ProdutoResponseDTO> produtos;

    public PedidoResponseDTO(Pedido pedido) {
        this.id = pedido.getId();
        this.mesa = pedido.getMesa();
        this.produtos = pedido.getProdutos()
                .stream()
                .map(ProdutoResponseDTO::new)
                .collect(Collectors.toList());
    }
}
