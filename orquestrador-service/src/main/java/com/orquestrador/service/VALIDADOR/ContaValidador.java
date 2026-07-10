package com.orquestrador.service.VALIDADOR;

import com.orquestrador.service.DTO.Responses.ContaResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

@Component
public class ContaValidador {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${conta.service.url}")
    private String contaServiceUrl;


    public ContaResponseDTO validarContaExistePorId(String contaId) {
        try {
            ContaResponseDTO conta = restTemplate.getForObject(
                    contaServiceUrl + "/conta/" + contaId,
                    ContaResponseDTO.class
            );
            if (conta == null) {
                throw new RuntimeException("Conta com id " + contaId + " não encontrada");
            }
            return conta;
        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("Conta com id " + contaId + " não encontrada");
        }
    }

    public ContaResponseDTO validarContaExistePorNumero(String numeroConta) {
        try {
            ContaResponseDTO conta = restTemplate.getForObject(
                    contaServiceUrl + "/buscar/numero/" + numeroConta,
                    ContaResponseDTO.class
            );
            if (conta == null) {
                throw new RuntimeException("Conta " + numeroConta + " não encontrada");
            }
            return conta;
        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("Conta " + numeroConta + " não encontrada");
        }
    }

    // NOVO — busca todas as contas de um cliente
    public List<ContaResponseDTO> buscarContasPorClienteId(Long clienteId) {
        return restTemplate.exchange(
                contaServiceUrl + "/cliente/" + clienteId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ContaResponseDTO>>() {}
        ).getBody();
    }

    public void validarContaAtiva(ContaResponseDTO conta) {
        if (!"ATIVA".equals(conta.getEstadoConta().toString())) {
            throw new RuntimeException("Conta " + conta.getNumeroConta() + " não está ativa");
        }
    }

    public void validarContasDiferentes(String origemId, String destinoId) {
        if (origemId.equals(destinoId)) {
            throw new RuntimeException("Conta de origem e destino não podem ser iguais");
        }
    }

    public void validarSaldoSuficiente(ContaResponseDTO conta, BigDecimal valor) {
        if (conta.getSaldo().compareTo(valor) < 0) {
            throw new RuntimeException("Saldo insuficiente na conta " + conta.getNumeroConta()
                    + ". Saldo actual: " + conta.getSaldo());
        }
    }
}
