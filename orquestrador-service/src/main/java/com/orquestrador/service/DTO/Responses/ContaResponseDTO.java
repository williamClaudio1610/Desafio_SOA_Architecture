package com.orquestrador.service.DTO.Responses;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ContaResponseDTO {
    private Long id;
    private String numeroConta;
    private BigDecimal saldo;
    private Long clienteId;
    private String estadoConta;

    public String getEstadoConta() {
        return estadoConta;
    }

    public void setEstadoConta(String estadoConta) {
        this.estadoConta = estadoConta;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }
}