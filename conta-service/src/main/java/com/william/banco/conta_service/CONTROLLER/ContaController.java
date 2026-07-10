package com.william.banco.conta_service.CONTROLLER;

import com.william.banco.conta_service.DTO.ContaAtualizarSaldoRequestDTO;
import com.william.banco.conta_service.DTO.ContaRequestDTO;
import com.william.banco.conta_service.DTO.ContaRequestSaldoDTO;
import com.william.banco.conta_service.DTO.ContaResponseDTO;
import com.william.banco.conta_service.REPOSITORY.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import  com.william.banco.conta_service.SERVICE.IContaService;

import java.util.List;

@RestController
@RequestMapping("/api/contas")
public class ContaController {

    @Autowired
    private IContaService contaService;

    @PostMapping("/criarConta")
    public ResponseEntity<ContaResponseDTO> criar(@RequestBody ContaRequestDTO dto) {
        return ResponseEntity.ok(contaService.criarConta(dto));
    }

    @GetMapping("/conta/{contaID}")
    public ResponseEntity <ContaResponseDTO> buscarContaID(@PathVariable Long contaID) {
        return ResponseEntity.ok(contaService.buscarPorId(contaID));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<ContaResponseDTO>> listarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(contaService.buscarPeloClienteID(clienteId));
    }

    @GetMapping("/listarTodas")
        public ResponseEntity<List<ContaResponseDTO>> listarTodas() {
            return ResponseEntity.ok(contaService.listarContas());
    }

    @PatchMapping("/{id}/desativarConta")
    public ResponseEntity<ContaResponseDTO> inativarConta(@PathVariable Long id) {
        return ResponseEntity.ok(contaService.desativarConta(id));
    }

    @PatchMapping("/{id}/ativarConta")
    public ResponseEntity<ContaResponseDTO> ativarConta(@PathVariable Long id) {
        return ResponseEntity.ok(contaService.ativarConta(id));
    }

    @PatchMapping("/atualizarSaldo")
    public ResponseEntity<ContaResponseDTO> atualizarSaldo(@RequestBody ContaAtualizarSaldoRequestDTO dto) {
        return ResponseEntity.ok(contaService.atualizarSaldo(dto));
    }

    @GetMapping("/numero/{numeroConta}")
    public ResponseEntity<ContaResponseDTO> buscarPorNumero(@PathVariable String numeroConta) {
        return ResponseEntity.ok(contaService.buscarPorNumeroConta(numeroConta));
    }
}
