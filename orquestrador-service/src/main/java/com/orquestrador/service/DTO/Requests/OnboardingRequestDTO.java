package com.orquestrador.service.DTO.Requests;

import com.orquestrador.service.ENUM.TipoConta;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OnboardingRequestDTO {
    private String nome;
    private String email;
    private String telefone;
    private TipoConta tipoConta;
    private BigDecimal depositoInicial;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public TipoConta getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(TipoConta tipoConta) {
        this.tipoConta = tipoConta;
    }

    public BigDecimal getDepositoInicial() {
        return depositoInicial;
    }

    public void setDepositoInicial(BigDecimal depositoInicial) {
        this.depositoInicial = depositoInicial;
    }
}
