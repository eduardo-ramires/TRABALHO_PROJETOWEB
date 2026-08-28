package com.herysson.menubackend.service;

import com.herysson.menubackend.dto.PedidoRequestDTO;
import com.herysson.menubackend.dto.PedidoResponseDTO;
import com.herysson.menubackend.model.Pedido;
import com.herysson.menubackend.model.Produto;
import com.herysson.menubackend.repository.PedidoRepository;
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
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private PedidoRepository repository;

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private PedidoService service;

    private Produto produto1;
    private Produto produto2;
    private Pedido pedido;
    private PedidoRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        produto1 = new Produto();
        produto1.setId(1L);
        produto1.setNome("Pizza Margherita");
        produto1.setPreco(35.00);
        produto1.setCategoria("Pizza");
        produto1.setDisponibilidade(true);

        produto2 = new Produto();
        produto2.setId(2L);
        produto2.setNome("Refrigerante");
        produto2.setPreco(8.00);
        produto2.setCategoria("Bebida");
        produto2.setDisponibilidade(true);

        pedido = new Pedido();
        pedido.setId(1L);
        pedido.setMesa(5);
        pedido.setProdutos(Arrays.asList(produto1, produto2));

        PedidoRequestDTO.ProdutoIdWrapper wrapper1 = new PedidoRequestDTO.ProdutoIdWrapper();
        wrapper1.setId(1L);
        PedidoRequestDTO.ProdutoIdWrapper wrapper2 = new PedidoRequestDTO.ProdutoIdWrapper();
        wrapper2.setId(2L);

        requestDTO = new PedidoRequestDTO();
        requestDTO.setMesa(5);
        requestDTO.setProdutos(Arrays.asList(wrapper1, wrapper2));
    }

    @Test
    void listarTodos_deveRetornarListaDePedidos() {
        when(repository.findAll()).thenReturn(List.of(pedido));

        List<PedidoResponseDTO> resultado = service.listarTodos();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getMesa()).isEqualTo(5);
        assertThat(resultado.get(0).getProdutos()).hasSize(2);
        verify(repository, times(1)).findAll();
    }

    @Test
    void listarTodos_quandoVazio_deveRetornarListaVazia() {
        when(repository.findAll()).thenReturn(List.of());

        List<PedidoResponseDTO> resultado = service.listarTodos();

        assertThat(resultado).isEmpty();
    }

    @Test
    void listarTodos_deveMappearProdutosDeCadaPedido() {
        when(repository.findAll()).thenReturn(List.of(pedido));

        List<PedidoResponseDTO> resultado = service.listarTodos();

        assertThat(resultado.get(0).getProdutos().get(0).getNome()).isEqualTo("Pizza Margherita");
        assertThat(resultado.get(0).getProdutos().get(1).getNome()).isEqualTo("Refrigerante");
    }

    @Test
    void buscarPorId_deveRetornarPedido() {
        when(repository.findById(1L)).thenReturn(Optional.of(pedido));

        PedidoResponseDTO resultado = service.buscarPorId(1L);

        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getMesa()).isEqualTo(5);
        assertThat(resultado.getProdutos()).hasSize(2);
    }

    @Test
    void buscarPorId_quandoNaoEncontrado_deveLancarExcecao() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.buscarPorId(99L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void criar_deveSalvarERetornarPedido() {
        when(produtoRepository.findAllById(anyList())).thenReturn(Arrays.asList(produto1, produto2));
        when(repository.save(any(Pedido.class))).thenReturn(pedido);

        PedidoResponseDTO resultado = service.criar(requestDTO);

        assertThat(resultado.getMesa()).isEqualTo(5);
        assertThat(resultado.getProdutos()).hasSize(2);
        verify(repository, times(1)).save(any(Pedido.class));
    }

    @Test
    void criar_deveBuscarProdutosPelosIds() {
        when(produtoRepository.findAllById(List.of(1L, 2L))).thenReturn(Arrays.asList(produto1, produto2));
        when(repository.save(any(Pedido.class))).thenReturn(pedido);

        service.criar(requestDTO);

        verify(produtoRepository, times(1)).findAllById(List.of(1L, 2L));
    }

    @Test
    void atualizar_deveAtualizarMesaEProdutos() {
        PedidoRequestDTO.ProdutoIdWrapper wrapper = new PedidoRequestDTO.ProdutoIdWrapper();
        wrapper.setId(1L);

        PedidoRequestDTO dtoAtualizado = new PedidoRequestDTO();
        dtoAtualizado.setMesa(10);
        dtoAtualizado.setProdutos(List.of(wrapper));

        Pedido pedidoAtualizado = new Pedido();
        pedidoAtualizado.setId(1L);
        pedidoAtualizado.setMesa(10);
        pedidoAtualizado.setProdutos(List.of(produto1));

        when(repository.findById(1L)).thenReturn(Optional.of(pedido));
        when(produtoRepository.findAllById(List.of(1L))).thenReturn(List.of(produto1));
        when(repository.save(any(Pedido.class))).thenReturn(pedidoAtualizado);

        PedidoResponseDTO resultado = service.atualizar(1L, dtoAtualizado);

        assertThat(resultado.getMesa()).isEqualTo(10);
        assertThat(resultado.getProdutos()).hasSize(1);
        verify(repository).findById(1L);
        verify(repository).save(any(Pedido.class));
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
