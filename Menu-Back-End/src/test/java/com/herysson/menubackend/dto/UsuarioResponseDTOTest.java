package com.herysson.menubackend.dto;

import com.herysson.menubackend.model.Usuario;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class UsuarioResponseDTOTest {

    @Test
    void construtor_comUsuario_deveMappearTodosOsCampos() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Carlos");
        usuario.setMesa(3);
        usuario.setTipo("USUARIO");
        usuario.setSenha("senha123");

        UsuarioResponseDTO dto = new UsuarioResponseDTO(usuario);

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getNome()).isEqualTo("Carlos");
        assertThat(dto.getMesa()).isEqualTo(3);
        assertThat(dto.getTipo()).isEqualTo("USUARIO");
        assertThat(dto.getSenha()).isEqualTo("senha123");
    }

    @Test
    void construtor_comUsuarioAdm_deveMappearTipoAdm() {
        Usuario admin = new Usuario();
        admin.setId(2L);
        admin.setNome("Admin");
        admin.setMesa(0);
        admin.setTipo("ADM");
        admin.setSenha("adminPass");

        UsuarioResponseDTO dto = new UsuarioResponseDTO(admin);

        assertThat(dto.getTipo()).isEqualTo("ADM");
        assertThat(dto.getNome()).isEqualTo("Admin");
    }

    @Test
    void construtor_comTodosArgs_deveFuncionarCorretamente() {
        UsuarioResponseDTO dto = new UsuarioResponseDTO(5L, "Maria", 2, "USUARIO", "pass");

        assertThat(dto.getId()).isEqualTo(5L);
        assertThat(dto.getNome()).isEqualTo("Maria");
        assertThat(dto.getMesa()).isEqualTo(2);
        assertThat(dto.getTipo()).isEqualTo("USUARIO");
        assertThat(dto.getSenha()).isEqualTo("pass");
    }

    @Test
    void construtor_comUsuarioSemMesa_deveMappearMesaNula() {
        Usuario usuario = new Usuario();
        usuario.setId(3L);
        usuario.setNome("Teste");
        usuario.setMesa(null);
        usuario.setTipo("USUARIO");

        UsuarioResponseDTO dto = new UsuarioResponseDTO(usuario);

        assertThat(dto.getMesa()).isNull();
    }

    @Test
    void gettersESetters_devemFuncionarCorretamente() {
        UsuarioResponseDTO dto = new UsuarioResponseDTO(null, null, null, null, null);
        dto.setId(10L);
        dto.setNome("João");
        dto.setTipo("ADM");

        assertThat(dto.getId()).isEqualTo(10L);
        assertThat(dto.getNome()).isEqualTo("João");
        assertThat(dto.getTipo()).isEqualTo("ADM");
    }
}
