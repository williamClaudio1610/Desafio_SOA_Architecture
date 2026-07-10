package com.orquestrador.service.DTO.Responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContaComClienteResponseDTO {
    private ClienteResponseDTO cliente;
    private ContaResponseDTO conta;
}
