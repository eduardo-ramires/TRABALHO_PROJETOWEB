package com.herysson.menubackend.dto;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class PedidoRequestDTOTest {

    @Test
    void getProdutosIds_deveRetornarListaDeIds() {
        PedidoRequestDTO.ProdutoIdWrapper w1 = new PedidoRequestDTO.ProdutoIdWrapper();
        w1.setId(1L);
        PedidoRequestDTO.ProdutoIdWrapper w2 = new PedidoRequestDTO.ProdutoIdWrapper();
        w2.setId(2L);
        PedidoRequestDTO.ProdutoIdWrapper w3 = new PedidoRequestDTO.ProdutoIdWrapper();
        w3.setId(3L);

        PedidoRequestDTO dto = new PedidoRequestDTO();
        dto.setMesa(5);
        dto.setProdutos(Arrays.asList(w1, w2, w3));

        List<Long> ids = dto.getProdutosIds();

        assertThat(ids).containsExactly(1L, 2L, 3L);
    }

    @Test
    void getProdutosIds_quandoListaVazia_deveRetornarListaVazia() {
        PedidoRequestDTO dto = new PedidoRequestDTO();
        dto.setMesa(1);
        dto.setProdutos(List.of());

        List<Long> ids = dto.getProdutosIds();

        assertThat(ids).isEmpty();
    }

    @Test
    void getProdutosIds_devePreservarOrdemDosIds() {
        PedidoRequestDTO.ProdutoIdWrapper w1 = new PedidoRequestDTO.ProdutoIdWrapper();
        w1.setId(10L);
        PedidoRequestDTO.ProdutoIdWrapper w2 = new PedidoRequestDTO.ProdutoIdWrapper();
        w2.setId(5L);
        PedidoRequestDTO.ProdutoIdWrapper w3 = new PedidoRequestDTO.ProdutoIdWrapper();
        w3.setId(7L);

        PedidoRequestDTO dto = new PedidoRequestDTO();
        dto.setProdutos(Arrays.asList(w1, w2, w3));

        List<Long> ids = dto.getProdutosIds();

        assertThat(ids).containsExactly(10L, 5L, 7L);
    }

    @Test
    void gettersESetters_devemFuncionarCorretamente() {
        PedidoRequestDTO dto = new PedidoRequestDTO();
        dto.setMesa(8);

        assertThat(dto.getMesa()).isEqualTo(8);
    }

    @Test
    void produtoIdWrapper_getterESetter_devemFuncionarCorretamente() {
        PedidoRequestDTO.ProdutoIdWrapper wrapper = new PedidoRequestDTO.ProdutoIdWrapper();
        wrapper.setId(42L);

        assertThat(wrapper.getId()).isEqualTo(42L);
    }

    @Test
    void getProdutosIds_comUmProduto_deveRetornarListaComUmId() {
        PedidoRequestDTO.ProdutoIdWrapper wrapper = new PedidoRequestDTO.ProdutoIdWrapper();
        wrapper.setId(99L);

        PedidoRequestDTO dto = new PedidoRequestDTO();
        dto.setMesa(2);
        dto.setProdutos(List.of(wrapper));

        List<Long> ids = dto.getProdutosIds();

        assertThat(ids).hasSize(1).contains(99L);
    }
}
