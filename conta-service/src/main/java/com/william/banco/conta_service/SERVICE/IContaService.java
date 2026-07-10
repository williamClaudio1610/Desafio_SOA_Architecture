package com.william.banco.conta_service.SERVICE;

import com.william.banco.conta_service.DTO.ContaAtualizarSaldoRequestDTO;
import com.william.banco.conta_service.DTO.ContaRequestDTO;
import com.william.banco.conta_service.DTO.ContaRequestSaldoDTO;
import com.william.banco.conta_service.DTO.ContaResponseDTO;

import java.math.BigDecimal;
import java.util.List;

public interface IContaService {
    ContaResponseDTO criarConta(ContaRequestDTO dto);
    List<ContaResponseDTO> listarContas();
    ContaResponseDTO atualizarSaldo(ContaAtualizarSaldoRequestDTO dto);
    ContaResponseDTO desativarConta(Long id);
    ContaResponseDTO ativarConta(Long id);
    ContaResponseDTO buscarPorId(Long id);
    List <ContaResponseDTO> buscarPeloClienteID(Long id);
    ContaResponseDTO buscarPorNumeroConta(String numeroconta);
}
