package com.william.banco.transferencia_service.DTO;

import com.william.banco.transferencia_service.ENTITY.ENUM.EstadoMovimento;
import com.william.banco.transferencia_service.ENTITY.ENUM.TipoMovimento;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class MovimentoRequestDTO {
    private TipoMovimento tipoMovimento;
    private BigDecimal valor;
    private Long contaOrigemId;
    private Long contaDestinoId;
    private String descricao;
}

