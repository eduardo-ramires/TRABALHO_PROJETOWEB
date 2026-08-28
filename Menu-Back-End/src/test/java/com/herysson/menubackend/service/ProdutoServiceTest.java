package com.herysson.menubackend.service;

import com.herysson.menubackend.dto.ProdutoRequestDTO;
import com.herysson.menubackend.dto.ProdutoResponseDTO;
import com.herysson.menubackend.model.Produto;
import com.herysson.menubackend.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository repository;

    @InjectMocks
    private ProdutoService service;

    private Produto produto;
    private ProdutoRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        produto = new Produto();
        produto.setId(1L);
        produto.setNome("Pizza Margherita");
        produto.setDescricao("Pizza com molho de tomate e mussarela");
        produto.setPreco(35.90);
        produto.setCategoria("Pizza");
        produto.setDisponibilidade(true);
        produto.setImagem("pizza.jpg");

        requestDTO = new ProdutoRequestDTO();
        requestDTO.setNome("Pizza Margherita");
        requestDTO.setDescricao("Pizza com molho de tomate e mussarela");
        requestDTO.setPreco(35.90);
        requestDTO.setCategoria("Pizza");
        requestDTO.setDisponibilidade(true);
        requestDTO.setImagem("pizza.jpg");
    }

    @Test
    void listarTodos_deveRetornarListaDeProdutos() {
        Produto outro = new Produto();
        outro.setId(2L);
        outro.setNome("Hamburguer");
        outro.setPreco(25.00);

        when(repository.findAll()).thenReturn(Arrays.asList(produto, outro));

        List<ProdutoResponseDTO> resultado = service.listarTodos();

        assertThat(resultado).hasSize(2);
        assertThat(resultado.get(0).getNome()).isEqualTo("Pizza Margherita");
        assertThat(resultado.get(1).getNome()).isEqualTo("Hamburguer");
        verify(repository, times(1)).findAll();
    }

    @Test
    void listarTodos_quandoVazio_deveRetornarListaVazia() {
        when(repository.findAll()).thenReturn(List.of());

        List<ProdutoResponseDTO> resultado = service.listarTodos();

        assertThat(resultado).isEmpty();
    }

    @Test
    void buscarPorId_deveRetornarProduto() {
        when(repository.findById(1L)).thenReturn(Optional.of(produto));

        ProdutoResponseDTO resultado = service.buscarPorId(1L);

        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getNome()).isEqualTo("Pizza Margherita");
        assertThat(resultado.getPreco()).isEqualTo(35.90);
        assertThat(resultado.getCategoria()).isEqualTo("Pizza");
        assertThat(resultado.getDisponibilidade()).isTrue();
    }

    @Test
    void buscarPorId_quandoNaoEncontrado_deveLancarExcecao() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.buscarPorId(99L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void criar_deveSalvarERetornarProduto() {
        when(repository.save(any(Produto.class))).thenReturn(produto);

        ProdutoResponseDTO resultado = service.criar(requestDTO);

        assertThat(resultado.getNome()).isEqualTo("Pizza Margherita");
        assertThat(resultado.getPreco()).isEqualTo(35.90);
        assertThat(resultado.getCategoria()).isEqualTo("Pizza");
        assertThat(resultado.getDisponibilidade()).isTrue();
        verify(repository, times(1)).save(any(Produto.class));
    }

    @Test
    void criar_devePreencherTodosOsCamposDoProduto() {
        when(repository.save(any(Produto.class))).thenAnswer(invocation -> {
            Produto p = invocation.getArgument(0);
            p.setId(1L);
            return p;
        });

        ProdutoResponseDTO resultado = service.criar(requestDTO);

        assertThat(resultado.getNome()).isEqualTo(requestDTO.getNome());
        assertThat(resultado.getDescricao()).isEqualTo(requestDTO.getDescricao());
        assertThat(resultado.getPreco()).isEqualTo(requestDTO.getPreco());
        assertThat(resultado.getCategoria()).isEqualTo(requestDTO.getCategoria());
        assertThat(resultado.getDisponibilidade()).isEqualTo(requestDTO.getDisponibilidade());
        assertThat(resultado.getImagem()).isEqualTo(requestDTO.getImagem());
    }

    @Test
    void atualizar_deveAtualizarERetornarProduto() {
        ProdutoRequestDTO dtoAtualizado = new ProdutoRequestDTO();
        dtoAtualizado.setNome("Pizza 4 Queijos");
        dtoAtualizado.setDescricao("Pizza especial");
        dtoAtualizado.setPreco(42.00);
        dtoAtualizado.setCategoria("Pizza");
        dtoAtualizado.setDisponibilidade(false);
        dtoAtualizado.setImagem("pizza4q.jpg");

        Produto produtoAtualizado = new Produto();
        produtoAtualizado.setId(1L);
        produtoAtualizado.setNome("Pizza 4 Queijos");
        produtoAtualizado.setDescricao("Pizza especial");
        produtoAtualizado.setPreco(42.00);
        produtoAtualizado.setCategoria("Pizza");
        produtoAtualizado.setDisponibilidade(false);
        produtoAtualizado.setImagem("pizza4q.jpg");

        when(repository.findById(1L)).thenReturn(Optional.of(produto));
        when(repository.save(any(Produto.class))).thenReturn(produtoAtualizado);

        ProdutoResponseDTO resultado = service.atualizar(1L, dtoAtualizado);

        assertThat(resultado.getNome()).isEqualTo("Pizza 4 Queijos");
        assertThat(resultado.getPreco()).isEqualTo(42.00);
        assertThat(resultado.getDisponibilidade()).isFalse();
        verify(repository).findById(1L);
        verify(repository).save(any(Produto.class));
    }

    @Test
    void atualizar_quandoNaoEncontrado_deveLancarExcecao() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.atualizar(99L, requestDTO))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void deletar_deveChamarDeleteById() {
        doNothing().when(repository).deleteById(1L);

        service.deletar(1L);

        verify(repository, times(1)).deleteById(1L);
    }
}
