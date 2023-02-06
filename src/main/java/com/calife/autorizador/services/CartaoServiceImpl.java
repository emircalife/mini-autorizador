package com.calife.autorizador.services;

import com.calife.autorizador.domain.Cartao;
import com.calife.autorizador.domain.dtos.CartaoDTO;
import com.calife.autorizador.repositories.CartaoRepository;
import com.calife.autorizador.services.exceptions.ObjectNotFoundException;
import com.calife.autorizador.services.exceptions.ValidatorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartaoServiceImpl implements CartaoService {
    @Autowired
    private CartaoRepository repository;

    @Override
    public Cartao findByNumeroCartao(String numeroCartao) {
        Optional<Cartao> obj = repository.findByNumeroCartao(numeroCartao);
        return obj.orElseThrow(() -> new ObjectNotFoundException("CARTAO_INEXISTENTE"));
    }

    @Override
    public Cartao create(CartaoDTO objDTO) {
        objDTO.setNumeroCartao(objDTO.getNumeroCartao());
        objDTO.setSenhaCartao(objDTO.getSenhaCartao());
        objDTO.setValor(500.00);

        Cartao newObj = new Cartao(objDTO);
        return repository.save(newObj);
    }

    @Override
    public Cartao transacao(String numeroCartao, String senha, Double valorTransacao) {
        CartaoDTO objDTO = new CartaoDTO();


        objDTO.setNumeroCartao(numeroCartao);
        Cartao objResultDTO = findByNumeroCartao(numeroCartao);

        if(objResultDTO.getSenhaCartao().equals(senha)) {
            objResultDTO.setValor(valorTransacao);

            validaTransacao(objDTO);
            objResultDTO.setValor(objDTO.getValor());

            return repository.save(objResultDTO);
        }

        throw new ValidatorException("Senha incorreta", HttpStatus.FORBIDDEN);
    }

    private void validaTransacao(CartaoDTO objDTO) {
        //O cartão existir
        Optional<Cartao> obj = repository.findByNumeroCartao(objDTO.getNumeroCartao());
        if(obj.isPresent() && obj.get().getNumeroCartao() != objDTO.getNumeroCartao()) {
            throw new ObjectNotFoundException("CARTAO_INEXISTENTE");
        }

        //A senha do cartão for a correta
        if(obj.isPresent() && obj.get().getSenhaCartao() != objDTO.getSenhaCartao()) {
            throw new ObjectNotFoundException("SENHA_INVALIDA");
        }

        //O cartão possuir saldo disponível para a transação
        if(obj.isPresent() && obj.get().getValor() < objDTO.getValor()) {
            throw new ObjectNotFoundException("SALDO_INSUFICIENTE");
        }
    }
}
