package com.orquestrador.service.VALIDADOR;

import com.orquestrador.service.DTO.Requests.MovimentoRequestDTO;
import com.orquestrador.service.DTO.Responses.ContaResponseDTO;
import com.orquestrador.service.DTO.Responses.MovimentoResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class MovimentoValidador {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ContaValidador contaValidator;

    @Value("${movimentos.service.url}")
    private String movimentosServiceUrl;

    public void validarContaDestinoObrigatoria(MovimentoRequestDTO dto) {
        if (dto.getContaDestinoId() == null) {
            throw new RuntimeException("Conta destino é obrigatória para transferências");
        }
    }

    public void validarSemContaDestino(MovimentoRequestDTO dto) {
        if (dto.getContaDestinoId() != null) {
            throw new RuntimeException("Esta operação não deve informar conta destino");
        }
    }

    // NOVO — busca movimentos a partir do número da conta
    public List<MovimentoResponseDTO> buscarMovimentosPorNumeroConta(String numeroConta) {
        ContaResponseDTO conta = contaValidator.validarContaExistePorNumero(numeroConta);
        return restTemplate.exchange(
                movimentosServiceUrl + "/conta/" + conta.getId(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<MovimentoResponseDTO>>() {}
        ).getBody();
    }

    // NOVO — busca movimentos de todas as contas de um cliente
    public List<MovimentoResponseDTO> buscarMovimentosPorClienteId(Long clienteId) {
        List<ContaResponseDTO> contas = contaValidator.buscarContasPorClienteId(clienteId);

        List<MovimentoResponseDTO> todos = new ArrayList<>();
        for (ContaResponseDTO conta : contas) {
            List<MovimentoResponseDTO> movimentos = restTemplate.exchange(
                    movimentosServiceUrl + "/conta/" + conta.getId(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<MovimentoResponseDTO>>() {}
            ).getBody();
            todos.addAll(movimentos);
        }
        return todos;
    }
}
