package com.herysson.menubackend.dto;

import com.herysson.menubackend.model.Produto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ProdutoResponseDTOTest {

    @Test
    void construtor_comProduto_deveMappearTodosOsCampos() {
        Produto produto = new Produto();
        produto.setId(10L);
        produto.setNome("Pizza Margherita");
        produto.setDescricao("Pizza clássica com molho e mussarela");
        produto.setPreco(35.90);
        produto.setCategoria("Pizza");
        produto.setDisponibilidade(true);
        produto.setImagem("pizza.jpg");

        ProdutoResponseDTO dto = new ProdutoResponseDTO(produto);

        assertThat(dto.getId()).isEqualTo(10L);
        assertThat(dto.getNome()).isEqualTo("Pizza Margherita");
        assertThat(dto.getDescricao()).isEqualTo("Pizza clássica com molho e mussarela");
        assertThat(dto.getPreco()).isEqualTo(35.90);
        assertThat(dto.getCategoria()).isEqualTo("Pizza");
        assertThat(dto.getDisponibilidade()).isTrue();
        assertThat(dto.getImagem()).isEqualTo("pizza.jpg");
    }

    @Test
    void construtor_comProdutoIndisponivel_deveMappearDisponibilidadeFalse() {
        Produto produto = new Produto();
        produto.setId(2L);
        produto.setNome("Hamburguer");
        produto.setDisponibilidade(false);

        ProdutoResponseDTO dto = new ProdutoResponseDTO(produto);

        assertThat(dto.getDisponibilidade()).isFalse();
    }

    @Test
    void construtor_comTodosArgs_deveFuncionarCorretamente() {
        ProdutoResponseDTO dto = new ProdutoResponseDTO(1L, "Pizza", "Desc", 30.0, "Pizza", true, "img.jpg");

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getNome()).isEqualTo("Pizza");
        assertThat(dto.getDescricao()).isEqualTo("Desc");
        assertThat(dto.getPreco()).isEqualTo(30.0);
        assertThat(dto.getCategoria()).isEqualTo("Pizza");
        assertThat(dto.getDisponibilidade()).isTrue();
        assertThat(dto.getImagem()).isEqualTo("img.jpg");
    }

    @Test
    void construtor_comProdutoSemImagem_deveMappearImagemNula() {
        Produto produto = new Produto();
        produto.setId(3L);
        produto.setNome("Suco");
        produto.setImagem(null);

        ProdutoResponseDTO dto = new ProdutoResponseDTO(produto);

        assertThat(dto.getImagem()).isNull();
    }

    @Test
    void gettersESetters_devemFuncionarCorretamente() {
        ProdutoResponseDTO dto = new ProdutoResponseDTO(null, null, null, null, null, null, null);
        dto.setId(5L);
        dto.setNome("Bebida");
        dto.setPreco(10.0);

        assertThat(dto.getId()).isEqualTo(5L);
        assertThat(dto.getNome()).isEqualTo("Bebida");
        assertThat(dto.getPreco()).isEqualTo(10.0);
    }
}
