package com.herysson.menubackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.herysson.menubackend.model.Usuario;


@Data
@AllArgsConstructor
public class UsuarioResponseDTO {
    private Long id;
    private String nome;
    private Integer mesa;
    private String tipo;
    private String senha;


    public UsuarioResponseDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.mesa = usuario.getMesa();
        this.tipo = usuario.getTipo();
        this.senha = usuario.getSenha();
    }
}