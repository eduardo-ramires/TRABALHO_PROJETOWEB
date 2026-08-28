package com.herysson.menubackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.herysson.menubackend.dto.UsuarioRequestDTO;
import com.herysson.menubackend.dto.UsuarioResponseDTO;
import com.herysson.menubackend.service.UsuarioService;
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

@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService service;

    @Autowired
    private ObjectMapper objectMapper;

    private UsuarioResponseDTO responseDTO;
    private UsuarioRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        responseDTO = new UsuarioResponseDTO(1L, "Carlos", 3, "USUARIO", "senha123");

        requestDTO = new UsuarioRequestDTO();
        requestDTO.setNome("Carlos");
        requestDTO.setMesa(3);
        requestDTO.setTipo("USUARIO");
        requestDTO.setSenha("senha123");
    }

    @Test
    void listarTodos_deveRetornar200ComListaDeUsuarios() throws Exception {
        UsuarioResponseDTO admin = new UsuarioResponseDTO(2L, "Admin", 0, "ADM", "adminPass");
        when(service.listarTodos()).thenReturn(List.of(responseDTO, admin));

        mockMvc.perform(get("/usuarios"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nome").value("Carlos"))
                .andExpect(jsonPath("$[0].tipo").value("USUARIO"))
                .andExpect(jsonPath("$[1].tipo").value("ADM"));
    }

    @Test
    void listarTodos_quandoVazio_deveRetornar200ComListaVazia() throws Exception {
        when(service.listarTodos()).thenReturn(List.of());

        mockMvc.perform(get("/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void buscarPorId_deveRetornar200ComUsuario() throws Exception {
        when(service.buscarPorId(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Carlos"))
                .andExpect(jsonPath("$.mesa").value(3))
                .andExpect(jsonPath("$.tipo").value("USUARIO"));
    }

    @Test
    void buscarPorId_quandoNaoEncontrado_deveRetornar500() throws Exception {
        when(service.buscarPorId(99L)).thenThrow(new NoSuchElementException());

        mockMvc.perform(get("/usuarios/99"))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void criar_deveRetornar200ComUsuarioCriado() throws Exception {
        when(service.criar(any(UsuarioRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Carlos"))
                .andExpect(jsonPath("$.mesa").value(3));
    }

    @Test
    void criar_comTipoAdm_deveRetornarUsuarioAdm() throws Exception {
        UsuarioResponseDTO adminResponse = new UsuarioResponseDTO(2L, "Admin", 0, "ADM", "adminPass");
        when(service.criar(any(UsuarioRequestDTO.class))).thenReturn(adminResponse);

        UsuarioRequestDTO dtoAdm = new UsuarioRequestDTO();
        dtoAdm.setNome("Admin");
        dtoAdm.setMesa(0);
        dtoAdm.setTipo("ADM");
        dtoAdm.setSenha("adminPass");

        mockMvc.perform(post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoAdm)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tipo").value("ADM"));
    }

    @Test
    void atualizar_deveRetornar200ComUsuarioAtualizado() throws Exception {
        UsuarioResponseDTO atualizado = new UsuarioResponseDTO(1L, "Carlos Novo", 7, "ADM", "novaSenha");
        when(service.atualizar(eq(1L), any(UsuarioRequestDTO.class))).thenReturn(atualizado);

        UsuarioRequestDTO dtoAtualizado = new UsuarioRequestDTO();
        dtoAtualizado.setNome("Carlos Novo");
        dtoAtualizado.setMesa(7);
        dtoAtualizado.setTipo("ADM");
        dtoAtualizado.setSenha("novaSenha");

        mockMvc.perform(put("/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoAtualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Carlos Novo"))
                .andExpect(jsonPath("$.mesa").value(7))
                .andExpect(jsonPath("$.tipo").value("ADM"));
    }

    @Test
    void deletar_deveRetornar200() throws Exception {
        doNothing().when(service).deletar(1L);

        mockMvc.perform(delete("/usuarios/1"))
                .andExpect(status().isOk());

        verify(service, times(1)).deletar(1L);
    }
}
