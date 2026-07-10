package com.william.banco.conta_service.DTO;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ContaRequestSaldoDTO {
    private BigDecimal valor;
}
