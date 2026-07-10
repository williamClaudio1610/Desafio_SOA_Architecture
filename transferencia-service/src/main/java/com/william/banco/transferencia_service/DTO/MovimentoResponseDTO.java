package com.william.banco.transferencia_service.DTO;

import com.william.banco.transferencia_service.ENTITY.ENUM.EstadoMovimento;
import com.william.banco.transferencia_service.ENTITY.ENUM.TipoMovimento;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class MovimentoResponseDTO {
    private Long id;
    private TipoMovimento tipoMovimento;
    private LocalDateTime dataMovimento;
    private BigDecimal valor;
    private Long contaOrigemId;
    private Long contaDestinoId; // null se não for transferência
    private EstadoMovimento estadoMovimento;
    private String descricao;
    private String numOperacao;

    public String getNumOperacao() {
        return numOperacao;
    }

    public void setNumOperacao(String numOperacao) {
        this.numOperacao = numOperacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDataMovimento() {
        return dataMovimento;
    }

    public void setDataMovimento(LocalDateTime dataMovimento) {
        this.dataMovimento = dataMovimento;
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

    public EstadoMovimento getEstadoMovimento() {
        return estadoMovimento;
    }

    public void setEstadoMovimento(EstadoMovimento estadoMovimento) {
        this.estadoMovimento = estadoMovimento;
    }
}
