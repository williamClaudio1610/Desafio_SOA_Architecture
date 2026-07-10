package com.orquestrador.service.DTO.Responses;

import com.orquestrador.service.ENUM.TipoMovimento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MovimentoResponseDTO {
    private Long id;
    private TipoMovimento tipoMovimento;
    private LocalDateTime dataMovimento;
    private BigDecimal valor;
    private Long contaOrigemId;
    private Long contaDestinoId; // null se não for transferência
    private String estadoMovimento;
    private String descricao;
    private String numOperacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoMovimento getTipoMovimento() {
        return tipoMovimento;
    }

    public void setTipoMovimento(TipoMovimento tipoMovimento) {
        this.tipoMovimento = tipoMovimento;
    }

    public LocalDateTime getDataMovimento() {
        return dataMovimento;
    }

    public void setDataMovimento(LocalDateTime dataMovimento) {
        this.dataMovimento = dataMovimento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Long getContaOrigemId() {
        return contaOrigemId;
    }

    public void setContaOrigemId(Long contaOrigemId) {
        this.contaOrigemId = contaOrigemId;
    }

    public Long getContaDestinoId() {
        return contaDestinoId;
    }

    public void setContaDestinoId(Long contaDestinoId) {
        this.contaDestinoId = contaDestinoId;
    }

    public String getEstadoMovimento() {
        return estadoMovimento;
    }

    public void setEstadoMovimento(String estadoMovimento) {
        this.estadoMovimento = estadoMovimento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNumOperacao() {
        return numOperacao;
    }

    public void setNumOperacao(String numOperacao) {
        this.numOperacao = numOperacao;
    }
}
