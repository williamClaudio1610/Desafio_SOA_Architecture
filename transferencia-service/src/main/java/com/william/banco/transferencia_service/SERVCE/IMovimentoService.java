package com.william.banco.transferencia_service.SERVCE;

import com.william.banco.transferencia_service.DTO.MovimentoRequestDTO;
import com.william.banco.transferencia_service.DTO.MovimentoResponseDTO;

import java.util.List;

public interface IMovimentoService {
    MovimentoResponseDTO depositar(MovimentoRequestDTO dto);
    MovimentoResponseDTO levantar(MovimentoRequestDTO dto);
    MovimentoResponseDTO transferir(MovimentoRequestDTO dto);
    List<MovimentoResponseDTO> listarPorConta(Long contaId);
}
