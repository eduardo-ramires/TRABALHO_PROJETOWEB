package com.herysson.menubackend.dto;

import com.herysson.menubackend.model.Pedido;
import com.herysson.menubackend.model.Produto;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class PedidoResponseDTOTest {

    @Test
    void construtor_comPedido_deveMappearTodosOsCampos() {
        Produto produto1 = new Produto();
        produto1.setId(1L);
        produto1.setNome("Pizza");
        produto1.setPreco(35.0);
        produto1.setCategoria("Pizza");
        produto1.setDisponibilidade(true);

        Produto produto2 = new Produto();
        produto2.setId(2L);
        produto2.setNome("Refrigerante");
        produto2.setPreco(8.0);
        produto2.setCategoria("Bebida");
        produto2.setDisponibilidade(true);

        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setMesa(5);
        pedido.setProdutos(Arrays.asList(produto1, produto2));

        PedidoResponseDTO dto = new PedidoResponseDTO(pedido);

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getMesa()).isEqualTo(5);
        assertThat(dto.getProdutos()).hasSize(2);
    }

    @Test
    void construtor_comPedido_deveMappearProdutosCorretamente() {
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("Pizza Margherita");
        produto.setDescricao("Pizza deliciosa");
        produto.setPreco(35.90);
        produto.setCategoria("Pizza");
        produto.setDisponibilidade(true);
        produto.setImagem("pizza.jpg");

        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setMesa(3);
        pedido.setProdutos(List.of(produto));

        PedidoResponseDTO dto = new PedidoResponseDTO(pedido);

        assertThat(dto.getProdutos().get(0).getId()).isEqualTo(1L);
        assertThat(dto.getProdutos().get(0).getNome()).isEqualTo("Pizza Margherita");
        assertThat(dto.getProdutos().get(0).getPreco()).isEqualTo(35.90);
    }

    @Test
    void construtor_comPedidoSemProdutos_deveMappearListaVazia() {
        Pedido pedido = new Pedido();
        pedido.setId(2L);
        pedido.setMesa(10);
        pedido.setProdutos(List.of());

        PedidoResponseDTO dto = new PedidoResponseDTO(pedido);

        assertThat(dto.getProdutos()).isEmpty();
        assertThat(dto.getMesa()).isEqualTo(10);
    }

    @Test
    void construtor_comTodosArgs_deveFuncionarCorretamente() {
        List<ProdutoResponseDTO> produtos = List.of(
                new ProdutoResponseDTO(1L, "Pizza", "Desc", 35.0, "Pizza", true, "img.jpg")
        );

        PedidoResponseDTO dto = new PedidoResponseDTO(1L, 5, produtos);

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getMesa()).isEqualTo(5);
        assertThat(dto.getProdutos()).hasSize(1);
    }
}
