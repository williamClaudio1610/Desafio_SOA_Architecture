package com.william.banco.conta_service.DTO;

import com.william.banco.conta_service.ENTITY.StatusConta;
import com.william.banco.conta_service.ENTITY.TipoConta;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class ContaResponseDTO {
    private Long id;
    private String numeroConta;
    private BigDecimal saldo;
    private TipoConta tipoConta;
    private LocalDateTime dataCriacao;
    private StatusConta status;
    private Long clienteId;

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

    public TipoConta getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(TipoConta tipoConta) {
        this.tipoConta = tipoConta;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public StatusConta getStatus() {
        return status;
    }

    public void setStatus(StatusConta status) {
        this.status = status;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }
}