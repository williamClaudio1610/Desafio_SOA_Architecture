package com.orquestrador.service.DTO.Responses;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter @Setter
public class ExtratoContaResponseDTO {
    private ClienteResponseDTO cliente;
    private ContaResponseDTO conta;
    private List<MovimentoResponseDTO> movimentos;
}