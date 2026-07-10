package com.orquestrador.service.DTO;

import com.orquestrador.service.DTO.Responses.ContaResponseDTO;
import com.orquestrador.service.DTO.Responses.MovimentoResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
public class ContaComMovimentosDTO {
    private ContaResponseDTO conta;
    private List<MovimentoResponseDTO> movimentos;
}
