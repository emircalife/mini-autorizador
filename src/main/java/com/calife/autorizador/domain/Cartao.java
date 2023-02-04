package com.calife.autorizador.domain;

import com.calife.autorizador.domain.dtos.CartaoDTO;
import jakarta.persistence.*;


import java.io.Serializable;
@Entity
public class Cartao implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;
    @Column(unique = true)
    private String numeroCartao;
    private String senhaCartao;
    private Double valor;

    public Cartao() {
        super();
    }
    public Cartao(String numeroCartao, String senhaCartao, Double valor) {
        this.numeroCartao = numeroCartao;
        this.senhaCartao = senhaCartao;
        this.valor = valor;
    }

    public Cartao(CartaoDTO objDTO) {
        super();
        this.numeroCartao = objDTO.getNumeroCartao();
        this.senhaCartao = objDTO.getSenhaCartao();
        this.valor = objDTO.getValor();
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public String getSenhaCartao() {
        return senhaCartao;
    }

    public void setSenhaCartao(String senhaCartao) {
        this.senhaCartao = senhaCartao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}