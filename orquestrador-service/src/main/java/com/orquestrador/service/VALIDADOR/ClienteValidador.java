package com.orquestrador.service.VALIDADOR;

import com.orquestrador.service.DTO.Responses.ClienteResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class ClienteValidador {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${cliente.service.url}")
    private String clienteServiceUrl;

    public ClienteResponseDTO validarClienteExiste(Long clienteId) {
        try {
            ClienteResponseDTO cliente = restTemplate.getForObject(
                    clienteServiceUrl + "/buscar/" + clienteId,
                    ClienteResponseDTO.class
            );
            if (cliente == null) {
                throw new RuntimeException("Cliente com id " + clienteId + " não encontrado");
            }
            return cliente;
        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("Cliente com id " + clienteId + " não encontrado");
        }
    }
}
