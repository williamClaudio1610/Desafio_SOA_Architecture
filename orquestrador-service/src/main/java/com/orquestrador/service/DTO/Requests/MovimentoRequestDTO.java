package com.orquestrador.service.DTO.Requests;

import com.orquestrador.service.ENUM.TipoMovimento;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MovimentoRequestDTO {
    private TipoMovimento tipoMovimento;
    private BigDecimal valor;
    private String contaOrigemId;
    private String contaDestinoId;
    private String descricao;

    public TipoMovimento getTipoMovimento() {
        return tipoMovimento;
    }

    public void setTipoMovimento(TipoMovimento tipoMovimento) {
        this.tipoMovimento = tipoMovimento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getContaOrigemId() {
        return contaOrigemId;
    }

    public void setContaOrigemId(String contaOrigemId) {
        this.contaOrigemId = contaOrigemId;
    }

    public String getContaDestinoId() {
        return contaDestinoId;
    }

    public void setContaDestinoId(String contaDestinoId) {
        this.contaDestinoId = contaDestinoId;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
