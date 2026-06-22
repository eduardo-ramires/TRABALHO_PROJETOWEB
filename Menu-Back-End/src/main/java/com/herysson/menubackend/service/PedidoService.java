package com.herysson.menubackend.service;

import com.herysson.menubackend.dto.PedidoRequestDTO;
import com.herysson.menubackend.dto.PedidoResponseDTO;
import com.herysson.menubackend.model.Pedido;
import com.herysson.menubackend.model.Produto;
import com.herysson.menubackend.repository.PedidoRepository;
import com.herysson.menubackend.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private final PedidoRepository repository;
    private final ProdutoRepository produtoRepository;

    public PedidoService(PedidoRepository repository, ProdutoRepository produtoRepository) {
        this.repository = repository;
        this.produtoRepository = produtoRepository;
    }

    public List<PedidoResponseDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(PedidoResponseDTO::new)
                .collect(Collectors.toList());
    }

    public PedidoResponseDTO buscarPorId(Long id) {
        Pedido pedido = repository.findById(id).orElseThrow();
        return new PedidoResponseDTO(pedido);
    }

    public PedidoResponseDTO criar(PedidoRequestDTO dto) {
        Pedido pedido = new Pedido();
        preencherPedido(pedido, dto);

        Pedido salvo = repository.save(pedido);
        return new PedidoResponseDTO(salvo);
    }

    public PedidoResponseDTO atualizar(Long id, PedidoRequestDTO dto) {
        Pedido pedido = repository.findById(id).orElseThrow();
        preencherPedido(pedido, dto);

        Pedido atualizado = repository.save(pedido);
        return new PedidoResponseDTO(atualizado);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

    private void preencherPedido(Pedido pedido, PedidoRequestDTO dto) {
        pedido.setMesa(dto.getMesa());


        List<Produto> produtos = produtoRepository.findAllById(dto.getProdutosIds());
        pedido.setProdutos(produtos);
    }
}
