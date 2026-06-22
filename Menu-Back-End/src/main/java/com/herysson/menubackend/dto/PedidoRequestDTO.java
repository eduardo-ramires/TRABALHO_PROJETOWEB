package com.herysson.menubackend.dto;

import lombok.Data;

import java.util.List;


@Data
public class PedidoRequestDTO {
    private Integer mesa;
    private List<Long> produtosIds;
}
