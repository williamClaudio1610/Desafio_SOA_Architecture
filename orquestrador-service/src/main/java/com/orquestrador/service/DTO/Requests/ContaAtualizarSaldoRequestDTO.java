package com.orquestrador.service.DTO.Requests;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter @Setter
public class ContaAtualizarSaldoRequestDTO {
    private String numeroConta;
    private BigDecimal valor;
}