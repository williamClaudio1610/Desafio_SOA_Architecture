package com.orquestrador.service.DTO.Responses;

import com.orquestrador.service.DTO.ContaComMovimentosDTO;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter @Setter
public class ExtratoClienteResponseDTO {
    private ClienteResponseDTO cliente;
    private List<ContaComMovimentosDTO> contas;
}