package com.herysson.menubackend.service;

import com.herysson.menubackend.dto.UsuarioRequestDTO;
import com.herysson.menubackend.dto.UsuarioResponseDTO;
import com.herysson.menubackend.model.Usuario;
import com.herysson.menubackend.repository.UsuarioRepository;
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
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repository;

    @InjectMocks
    private UsuarioService service;

    private Usuario usuario;
    private UsuarioRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Carlos");
        usuario.setMesa(3);
        usuario.setTipo("USUARIO");
        usuario.setSenha("senha123");

        requestDTO = new UsuarioRequestDTO();
        requestDTO.setNome("Carlos");
        requestDTO.setMesa(3);
        requestDTO.setTipo("USUARIO");
        requestDTO.setSenha("senha123");
    }

    @Test
    void listarTodos_deveRetornarListaDeUsuarios() {
        Usuario admin = new Usuario();
        admin.setId(2L);
        admin.setNome("Admin");
        admin.setTipo("ADM");

        when(repository.findAll()).thenReturn(Arrays.asList(usuario, admin));

        List<UsuarioResponseDTO> resultado = service.listarTodos();

        assertThat(resultado).hasSize(2);
        assertThat(resultado.get(0).getNome()).isEqualTo("Carlos");
        assertThat(resultado.get(1).getNome()).isEqualTo("Admin");
        verify(repository, times(1)).findAll();
    }

    @Test
    void listarTodos_quandoVazio_deveRetornarListaVazia() {
        when(repository.findAll()).thenReturn(List.of());

        List<UsuarioResponseDTO> resultado = service.listarTodos();

        assertThat(resultado).isEmpty();
    }

    @Test
    void buscarPorId_deveRetornarUsuario() {
        when(repository.findById(1L)).thenReturn(Optional.of(usuario));

        UsuarioResponseDTO resultado = service.buscarPorId(1L);

        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getNome()).isEqualTo("Carlos");
        assertThat(resultado.getMesa()).isEqualTo(3);
        assertThat(resultado.getTipo()).isEqualTo("USUARIO");
    }

    @Test
    void buscarPorId_quandoNaoEncontrado_deveLancarExcecao() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.buscarPorId(99L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void criar_deveSalvarERetornarUsuario() {
        when(repository.save(any(Usuario.class))).thenReturn(usuario);

        UsuarioResponseDTO resultado = service.criar(requestDTO);

        assertThat(resultado.getNome()).isEqualTo("Carlos");
        assertThat(resultado.getMesa()).isEqualTo(3);
        assertThat(resultado.getTipo()).isEqualTo("USUARIO");
        verify(repository, times(1)).save(any(Usuario.class));
    }

    @Test
    void criar_devePreencherTodosOsCamposDoUsuario() {
        when(repository.save(any(Usuario.class))).thenAnswer(invocation -> {
            Usuario u = invocation.getArgument(0);
            u.setId(1L);
            return u;
        });

        UsuarioResponseDTO resultado = service.criar(requestDTO);

        assertThat(resultado.getNome()).isEqualTo(requestDTO.getNome());
        assertThat(resultado.getMesa()).isEqualTo(requestDTO.getMesa());
        assertThat(resultado.getTipo()).isEqualTo(requestDTO.getTipo());
        assertThat(resultado.getSenha()).isEqualTo(requestDTO.getSenha());
    }

    @Test
    void criar_comTipoAdm_deveSalvarCorretamente() {
        UsuarioRequestDTO dtoAdm = new UsuarioRequestDTO();
        dtoAdm.setNome("Admin Principal");
        dtoAdm.setMesa(0);
        dtoAdm.setTipo("ADM");
        dtoAdm.setSenha("adminPass");

        Usuario adminSalvo = new Usuario();
        adminSalvo.setId(2L);
        adminSalvo.setNome("Admin Principal");
        adminSalvo.setTipo("ADM");

        when(repository.save(any(Usuario.class))).thenReturn(adminSalvo);

        UsuarioResponseDTO resultado = service.criar(dtoAdm);

        assertThat(resultado.getTipo()).isEqualTo("ADM");
        assertThat(resultado.getNome()).isEqualTo("Admin Principal");
    }

    @Test
    void atualizar_deveAtualizarERetornarUsuario() {
        UsuarioRequestDTO dtoAtualizado = new UsuarioRequestDTO();
        dtoAtualizado.setNome("Carlos Novo");
        dtoAtualizado.setMesa(7);
        dtoAtualizado.setTipo("ADM");
        dtoAtualizado.setSenha("novaSenha");

        Usuario usuarioAtualizado = new Usuario();
        usuarioAtualizado.setId(1L);
        usuarioAtualizado.setNome("Carlos Novo");
        usuarioAtualizado.setMesa(7);
        usuarioAtualizado.setTipo("ADM");
        usuarioAtualizado.setSenha("novaSenha");

        when(repository.findById(1L)).thenReturn(Optional.of(usuario));
        when(repository.save(any(Usuario.class))).thenReturn(usuarioAtualizado);

        UsuarioResponseDTO resultado = service.atualizar(1L, dtoAtualizado);

        assertThat(resultado.getNome()).isEqualTo("Carlos Novo");
        assertThat(resultado.getMesa()).isEqualTo(7);
        assertThat(resultado.getTipo()).isEqualTo("ADM");
        verify(repository).findById(1L);
        verify(repository).save(any(Usuario.class));
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
