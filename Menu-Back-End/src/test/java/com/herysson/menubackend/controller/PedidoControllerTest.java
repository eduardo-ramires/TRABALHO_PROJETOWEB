package com.herysson.menubackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.herysson.menubackend.dto.PedidoRequestDTO;
import com.herysson.menubackend.dto.PedidoResponseDTO;
import com.herysson.menubackend.dto.ProdutoResponseDTO;
import com.herysson.menubackend.service.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PedidoController.class)
class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PedidoService service;

    @Autowired
    private ObjectMapper objectMapper;

    private PedidoResponseDTO responseDTO;
    private PedidoRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        ProdutoResponseDTO produto1 = new ProdutoResponseDTO(1L, "Pizza", "Desc", 35.0, "Pizza", true, "img.jpg");
        ProdutoResponseDTO produto2 = new ProdutoResponseDTO(2L, "Bebida", "Desc", 8.0, "Bebida", true, "img.jpg");

        responseDTO = new PedidoResponseDTO(1L, 5, Arrays.asList(produto1, produto2));

        PedidoRequestDTO.ProdutoIdWrapper w1 = new PedidoRequestDTO.ProdutoIdWrapper();
        w1.setId(1L);
        PedidoRequestDTO.ProdutoIdWrapper w2 = new PedidoRequestDTO.ProdutoIdWrapper();
        w2.setId(2L);

        requestDTO = new PedidoRequestDTO();
        requestDTO.setMesa(5);
        requestDTO.setProdutos(Arrays.asList(w1, w2));
    }

    @Test
    void listarTodos_deveRetornar200ComListaDePedidos() throws Exception {
        when(service.listarTodos()).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/pedidos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].mesa").value(5))
                .andExpect(jsonPath("$[0].produtos.length()").value(2));
    }

    @Test
    void listarTodos_quandoVazio_deveRetornar200ComListaVazia() throws Exception {
        when(service.listarTodos()).thenReturn(List.of());

        mockMvc.perform(get("/pedidos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void buscarPorId_deveRetornar200ComPedido() throws Exception {
        when(service.buscarPorId(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/pedidos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.mesa").value(5))
                .andExpect(jsonPath("$.produtos[0].nome").value("Pizza"))
                .andExpect(jsonPath("$.produtos[1].nome").value("Bebida"));
    }

    @Test
    void buscarPorId_quandoNaoEncontrado_deveRetornar404() throws Exception {
        when(service.buscarPorId(99L)).thenThrow(new NoSuchElementException());

        mockMvc.perform(get("/pedidos/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void criar_deveRetornar200ComPedidoCriado() throws Exception {
        when(service.criar(any(PedidoRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.mesa").value(5))
                .andExpect(jsonPath("$.produtos.length()").value(2));
    }

    @Test
    void atualizar_deveRetornar200ComPedidoAtualizado() throws Exception {
        PedidoResponseDTO atualizado = new PedidoResponseDTO(1L, 10, List.of(
                new ProdutoResponseDTO(1L, "Pizza", "Desc", 35.0, "Pizza", true, "img.jpg")
        ));
        when(service.atualizar(eq(1L), any(PedidoRequestDTO.class))).thenReturn(atualizado);

        PedidoRequestDTO.ProdutoIdWrapper w = new PedidoRequestDTO.ProdutoIdWrapper();
        w.setId(1L);
        PedidoRequestDTO dtoAtualizado = new PedidoRequestDTO();
        dtoAtualizado.setMesa(10);
        dtoAtualizado.setProdutos(List.of(w));

        mockMvc.perform(put("/pedidos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoAtualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mesa").value(10))
                .andExpect(jsonPath("$.produtos.length()").value(1));
    }

    @Test
    void deletar_deveRetornar200() throws Exception {
        doNothing().when(service).deletar(1L);

        mockMvc.perform(delete("/pedidos/1"))
                .andExpect(status().isOk());

        verify(service, times(1)).deletar(1L);
    }
}
