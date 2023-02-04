package com.calife.autorizador.domain.dtos;

import com.calife.autorizador.domain.Cartao;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public class CartaoDTO implements Serializable{
    protected Integer id;
    @NotNull(message = "Campo Número do Cartão é obrigatório")
    protected String numeroCartao;

    @NotNull(message = "Campo Senha é obrigatório")
    protected String senhaCartao;

    protected Double valor;

    public CartaoDTO() {
        super();
    }

    public CartaoDTO(Cartao oldObj) {
        super();
        this.id = oldObj.getId();
        this.numeroCartao = oldObj.getNumeroCartao();
        this.senhaCartao = oldObj.getSenhaCartao();
        this.valor = oldObj.getValor();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
