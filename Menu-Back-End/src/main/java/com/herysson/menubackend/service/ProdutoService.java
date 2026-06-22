package com.herysson.menubackend.service;

import com.herysson.menubackend.dto.ProdutoRequestDTO;
import com.herysson.menubackend.dto.ProdutoResponseDTO;
import com.herysson.menubackend.model.Produto;
import com.herysson.menubackend.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    public List<ProdutoResponseDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(ProdutoResponseDTO::new)
                .collect(Collectors.toList());
    }

    public ProdutoResponseDTO buscarPorId(Long id) {
        Produto produto = repository.findById(id).orElseThrow();
        return new ProdutoResponseDTO(produto);
    }

    public ProdutoResponseDTO criar(ProdutoRequestDTO dto) {
        Produto produto = new Produto();
        preencherProduto(produto, dto);

        Produto salvo = repository.save(produto);
        return new ProdutoResponseDTO(salvo);
    }

    public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO dto) {
        Produto produto = repository.findById(id).orElseThrow();
        preencherProduto(produto, dto);

        Produto atualizado = repository.save(produto);
        return new ProdutoResponseDTO(atualizado);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

    private void preencherProduto(Produto produto, ProdutoRequestDTO dto) {
        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setPreco(dto.getPreco());
        produto.setCategoria(dto.getCategoria());
        produto.setDisponibilidade(dto.getDisponibilidade());
        produto.setImagem(dto.getImagem());
    }
}
