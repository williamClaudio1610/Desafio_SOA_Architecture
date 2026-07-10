package com.orquestrador.service.DTO.Requests;

import com.orquestrador.service.ENUM.TipoConta;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ContaRequestDTO {
    private Long clienteId;
    private TipoConta tipoconta;
    private BigDecimal valor;

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public TipoConta getTipoconta() {
        return tipoconta;
    }

    public void setTipoconta(TipoConta tipoconta) {
        this.tipoconta = tipoconta;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}