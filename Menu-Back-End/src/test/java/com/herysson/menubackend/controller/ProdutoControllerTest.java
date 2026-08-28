package com.herysson.menubackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.herysson.menubackend.dto.ProdutoRequestDTO;
import com.herysson.menubackend.dto.ProdutoResponseDTO;
import com.herysson.menubackend.service.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProdutoController.class)
class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProdutoService service;

    @Autowired
    private ObjectMapper objectMapper;

    private ProdutoResponseDTO responseDTO;
    private ProdutoRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        responseDTO = new ProdutoResponseDTO(1L, "Pizza Margherita", "Deliciosa pizza", 35.90, "Pizza", true, "pizza.jpg");

        requestDTO = new ProdutoRequestDTO();
        requestDTO.setNome("Pizza Margherita");
        requestDTO.setDescricao("Deliciosa pizza");
        requestDTO.setPreco(35.90);
        requestDTO.setCategoria("Pizza");
        requestDTO.setDisponibilidade(true);
        requestDTO.setImagem("pizza.jpg");
    }

    @Test
    void listarTodos_deveRetornar200ComListaDeProdutos() throws Exception {
        ProdutoResponseDTO outro = new ProdutoResponseDTO(2L, "Hamburguer", "Burguer clássico", 25.00, "Lanche", true, "burger.jpg");
        when(service.listarTodos()).thenReturn(List.of(responseDTO, outro));

        mockMvc.perform(get("/produtos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nome").value("Pizza Margherita"))
                .andExpect(jsonPath("$[0].preco").value(35.90))
                .andExpect(jsonPath("$[1].nome").value("Hamburguer"));
    }

    @Test
    void listarTodos_quandoVazio_deveRetornar200ComListaVazia() throws Exception {
        when(service.listarTodos()).thenReturn(List.of());

        mockMvc.perform(get("/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void buscarPorId_deveRetornar200ComProduto() throws Exception {
        when(service.buscarPorId(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/produtos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Pizza Margherita"))
                .andExpect(jsonPath("$.categoria").value("Pizza"))
                .andExpect(jsonPath("$.disponibilidade").value(true));
    }

    @Test
    void buscarPorId_quandoNaoEncontrado_deveRetornar500() throws Exception {
        when(service.buscarPorId(99L)).thenThrow(new NoSuchElementException());

        mockMvc.perform(get("/produtos/99"))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void criar_deveRetornar200ComProdutoCriado() throws Exception {
        when(service.criar(any(ProdutoRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Pizza Margherita"))
                .andExpect(jsonPath("$.preco").value(35.90));
    }

    @Test
    void atualizar_deveRetornar200ComProdutoAtualizado() throws Exception {
        ProdutoResponseDTO atualizado = new ProdutoResponseDTO(1L, "Pizza 4 Queijos", "Nova pizza", 42.00, "Pizza", true, "pizza4q.jpg");
        when(service.atualizar(eq(1L), any(ProdutoRequestDTO.class))).thenReturn(atualizado);

        ProdutoRequestDTO dtoAtualizado = new ProdutoRequestDTO();
        dtoAtualizado.setNome("Pizza 4 Queijos");
        dtoAtualizado.setDescricao("Nova pizza");
        dtoAtualizado.setPreco(42.00);
        dtoAtualizado.setCategoria("Pizza");
        dtoAtualizado.setDisponibilidade(true);
        dtoAtualizado.setImagem("pizza4q.jpg");

        mockMvc.perform(put("/produtos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoAtualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Pizza 4 Queijos"))
                .andExpect(jsonPath("$.preco").value(42.00));
    }

    @Test
    void deletar_deveRetornar200() throws Exception {
        doNothing().when(service).deletar(1L);

        mockMvc.perform(delete("/produtos/1"))
                .andExpect(status().isOk());

        verify(service, times(1)).deletar(1L);
    }
}
