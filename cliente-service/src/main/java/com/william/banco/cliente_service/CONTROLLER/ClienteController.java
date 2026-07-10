package com.william.banco.cliente_service.CONTROLLER;

import com.william.banco.cliente_service.DTO.ClienteRequestDTO;
import com.william.banco.cliente_service.DTO.ClienteResponseDTO;
import com.william.banco.cliente_service.SERVICE.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> criarCliente(@RequestBody ClienteRequestDTO dto) {
        return ResponseEntity.ok(clienteService.criarCliente(dto));
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarClientes() {
        return ResponseEntity.ok(clienteService.listarTodos());
    }
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> atualizar(@PathVariable Long id, @RequestBody ClienteRequestDTO dto) {
        return ResponseEntity.ok(clienteService.atualizar(id, dto));
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<ClienteResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.buscarPorID(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        clienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
