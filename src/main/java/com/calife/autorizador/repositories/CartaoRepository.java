package com.calife.autorizador.repositories;

import com.calife.autorizador.domain.Cartao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartaoRepository extends JpaRepository<Cartao, Integer> {

    Optional<Cartao> findByNumeroCartao(String numeroCartao);
//    @Query("select * from miniautorizador where numeroCartao = :numeroCartao")
//    Optional<Cartao> findByNumeroCartao(String numeroCartao);
}