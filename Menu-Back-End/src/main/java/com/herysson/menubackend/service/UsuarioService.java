package com.herysson.menubackend.service;

import com.herysson.menubackend.dto.UsuarioRequestDTO;
import com.herysson.menubackend.dto.UsuarioResponseDTO;
import com.herysson.menubackend.model.Usuario;
import com.herysson.menubackend.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public List<UsuarioResponseDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(UsuarioResponseDTO::new)
                .collect(Collectors.toList());
    }

    public UsuarioResponseDTO buscarPorId(Long id) {
        Usuario usuario = repository.findById(id).orElseThrow();
        return new UsuarioResponseDTO(usuario);
    }

    public UsuarioResponseDTO criar(UsuarioRequestDTO dto) {
        Usuario usuario = new Usuario();
        preencherUsuario(usuario, dto);

        Usuario salvo = repository.save(usuario);
        return new UsuarioResponseDTO(salvo);
    }

    public UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO dto) {
        Usuario usuario = repository.findById(id).orElseThrow();
        preencherUsuario(usuario, dto);

        Usuario atualizado = repository.save(usuario);
        return new UsuarioResponseDTO(atualizado);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

    private void preencherUsuario(Usuario usuario, UsuarioRequestDTO dto) {
        usuario.setNome(dto.getNome());
        usuario.setMesa(dto.getMesa());
        usuario.setTipo(dto.getTipo());
        usuario.setSenha(dto.getSenha());
    }
}
