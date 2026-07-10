package com.william.banco.conta_service.ENTITY;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "contas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContaEntidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @Column(unique = true, nullable = false, length = 10)
    private String numeroConta;

    private BigDecimal saldo;

    @Enumerated(EnumType.STRING)
    private TipoConta tipoconta;

    private LocalDateTime dataCriacao;

    @Enumerated(EnumType.STRING)
    private StatusConta status;

    private Long clienteId;

    @PrePersist
    public void prePersist() {
        this.dataCriacao = LocalDateTime.now();
        if (this.status == null) this.status = StatusConta.ATIVA;
        if (this.saldo == null) this.saldo = BigDecimal.ZERO;
    }
}
