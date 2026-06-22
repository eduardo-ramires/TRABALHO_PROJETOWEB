package com.herysson.menubackend.controller;

import com.herysson.menubackend.dto.PedidoRequestDTO;
import com.herysson.menubackend.dto.PedidoResponseDTO;
import com.herysson.menubackend.service.PedidoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {

    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @GetMapping
    public List<PedidoResponseDTO> listarTodos() {
        return service.listarTodos();
    }

    @PostMapping
    public PedidoResponseDTO criar(@RequestBody PedidoRequestDTO dto) {
        return service.criar(dto);
    }

    @GetMapping("/{id}")
    public PedidoResponseDTO buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public PedidoResponseDTO atualizar(@PathVariable Long id, @RequestBody PedidoRequestDTO dto) {
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
        }

}