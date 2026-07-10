package com.william.banco.transferencia_service.ENTITY;

import com.william.banco.transferencia_service.ENTITY.ENUM.EstadoMovimento;
import com.william.banco.transferencia_service.ENTITY.ENUM.TipoMovimento;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimentos")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovimentoEntidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoMovimento tipoMovimento;

    private BigDecimal valor;

    private Long contaOrigemId;   // sempre preenchido
    private Long contaDestinoId;  // só em transferências

    private LocalDateTime dataMovimento;

    private String descricao;

    private String numOperacao;

    @Enumerated(EnumType.STRING)
    private EstadoMovimento status;

    @PrePersist
    public void prePersist() {
        this.dataMovimento = LocalDateTime.now();
        this.status = EstadoMovimento.CONCLUIDO;
    }

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

    public LocalDateTime getDataMovimento() {
        return dataMovimento;
    }

    public void setDataMovimento(LocalDateTime dataMovimento) {
        this.dataMovimento = dataMovimento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public EstadoMovimento getStatus() {
        return status;
    }

    public void setStatus(EstadoMovimento status) {
        this.status = status;
    }
}
