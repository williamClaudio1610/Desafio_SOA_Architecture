package com.william.banco.transferencia_service.CONTROLLER;


import com.william.banco.transferencia_service.DTO.MovimentoRequestDTO;
import com.william.banco.transferencia_service.DTO.MovimentoResponseDTO;
import com.william.banco.transferencia_service.SERVCE.IMovimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/movimentos")
public class MovimentoController {

    @Autowired
    private IMovimentoService movimentoService;

    @PostMapping("/depositar")
    public ResponseEntity<MovimentoResponseDTO> depositar(@RequestBody MovimentoRequestDTO dto) {
        return ResponseEntity.ok(movimentoService.depositar(dto));
    }

    @PostMapping("/levantar")
    public ResponseEntity<MovimentoResponseDTO> levantar(@RequestBody MovimentoRequestDTO dto) {
        return ResponseEntity.ok(movimentoService.levantar(dto));
    }

    @PostMapping("/transferir")
    public ResponseEntity<MovimentoResponseDTO> transferir(@RequestBody MovimentoRequestDTO dto) {
        return ResponseEntity.ok(movimentoService.transferir(dto));
    }

    @GetMapping("/conta/{contaId}")
    public ResponseEntity<List<MovimentoResponseDTO>> listarPorConta(@PathVariable Long contaId) {
        return ResponseEntity.ok(movimentoService.listarPorConta(contaId));
    }
}
