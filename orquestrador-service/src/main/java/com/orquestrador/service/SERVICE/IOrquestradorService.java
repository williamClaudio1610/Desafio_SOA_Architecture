package com.orquestrador.service.SERVICE;

import com.orquestrador.service.DTO.Requests.MovimentoRequestDTO;
import com.orquestrador.service.DTO.Requests.OnboardingRequestDTO;
import com.orquestrador.service.DTO.Responses.*;

import java.util.List;

public interface IOrquestradorService {
    OnboardingResponseDTO criarCliente(OnboardingRequestDTO dto);

    MovimentoResponseDTO realizarMovimento(MovimentoRequestDTO dto);

    List<MovimentoResponseDTO> buscarMovimentosPorNumeroConta(String numeroConta);
    List<MovimentoResponseDTO> buscarMovimentosPorClienteId(Long clienteId);

    ContaComClienteResponseDTO buscarContaComCliente(String numeroConta);
    List<ContaResponseDTO> listarContasDoCliente(Long clienteId);

    ExtratoContaResponseDTO buscarExtratoConta(String numeroConta);
    ExtratoClienteResponseDTO buscarExtratoCliente(Long clienteId);

    List<MovimentoResponseDTO> buscarMovimentosPorTipo(String numeroConta, String tipo);
    List<MovimentoResponseDTO> buscarUltimosMovimentos(String numeroConta, int quantidade);



}