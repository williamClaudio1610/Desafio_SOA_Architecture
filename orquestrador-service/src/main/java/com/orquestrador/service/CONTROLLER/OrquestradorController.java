package com.orquestrador.service.CONTROLLER;

import com.orquestrador.service.DTO.Requests.MovimentoRequestDTO;
import com.orquestrador.service.DTO.Requests.OnboardingRequestDTO;
import com.orquestrador.service.DTO.Responses.*;
import com.orquestrador.service.SERVICE.IOrquestradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orquestrador")
public class OrquestradorController {

    @Autowired
    private IOrquestradorService orquestradorService;

    @PostMapping("/onboarding/criar-cliente")
    public ResponseEntity<OnboardingResponseDTO> criarCliente(@RequestBody OnboardingRequestDTO dto) {
        return ResponseEntity.ok(orquestradorService.criarCliente(dto));
    }

    @PostMapping("/movimento")
    public ResponseEntity<MovimentoResponseDTO> realizarMovimento(@RequestBody MovimentoRequestDTO dto) {
        return ResponseEntity.ok(orquestradorService.realizarMovimento(dto));
    }

    @GetMapping("/conta/{numeroConta}/movimentos")
    public ResponseEntity<List<MovimentoResponseDTO>> movimentosPorConta(@PathVariable String numeroConta) {
        return ResponseEntity.ok(orquestradorService.buscarMovimentosPorNumeroConta(numeroConta));
    }

    @GetMapping("/cliente/{clienteId}/movimentos")
    public ResponseEntity<List<MovimentoResponseDTO>> movimentosPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(orquestradorService.buscarMovimentosPorClienteId(clienteId));
    }

    @GetMapping("/conta/{numeroConta}/cliente")
    public ResponseEntity<ContaComClienteResponseDTO> contaComCliente(@PathVariable String numeroConta) {
        return ResponseEntity.ok(orquestradorService.buscarContaComCliente(numeroConta));
    }

    @GetMapping("/cliente/{clienteId}/contas")
    public ResponseEntity<List<ContaResponseDTO>> contasDoCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(orquestradorService.listarContasDoCliente(clienteId));
    }

    @GetMapping("/conta/{numeroConta}/extrato")
    public ResponseEntity<ExtratoContaResponseDTO> extratoConta(@PathVariable String numeroConta) {
        return ResponseEntity.ok(orquestradorService.buscarExtratoConta(numeroConta));
    }

    @GetMapping("/cliente/{clienteId}/extrato")
    public ResponseEntity<ExtratoClienteResponseDTO> extratoCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(orquestradorService.buscarExtratoCliente(clienteId));
    }

    @GetMapping("/conta/{numeroConta}/movimentos/tipo/{tipo}")
    public ResponseEntity<List<MovimentoResponseDTO>> movimentosPorTipo(
            @PathVariable String numeroConta, @PathVariable String tipo) {
        return ResponseEntity.ok(orquestradorService.buscarMovimentosPorTipo(numeroConta, tipo));
    }

    @GetMapping("/conta/{numeroConta}/movimentos/ultimos/{quantidade}")
    public ResponseEntity<List<MovimentoResponseDTO>> ultimosMovimentos(
            @PathVariable String numeroConta, @PathVariable int quantidade) {
        return ResponseEntity.ok(orquestradorService.buscarUltimosMovimentos(numeroConta, quantidade));
    }
}