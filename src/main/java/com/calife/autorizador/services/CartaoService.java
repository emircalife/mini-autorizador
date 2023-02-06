package com.calife.autorizador.services;

import com.calife.autorizador.domain.Cartao;
import com.calife.autorizador.domain.dtos.CartaoDTO;

public interface CartaoService {
  Cartao findByNumeroCartao(String numeroCartao);

  Cartao create(CartaoDTO cartaoDTO);

  Cartao transacao(String numeroCartao, String senha, Double valorTransacao);
}