package com.calife.autorizador.services;

import com.calife.autorizador.domain.Cartao;
import com.calife.autorizador.domain.dtos.CartaoDTO;
import com.calife.autorizador.domain.enums.MensagemErro;
import com.calife.autorizador.repositories.CartaoRepository;
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
        return obj.orElseThrow(() -> new ValidatorException(String.valueOf(MensagemErro.CARTAO_INEXISTENTE), -2023));
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
        objDTO.setSenhaCartao(senha);

        Cartao objResultDTO = findByNumeroCartao(numeroCartao);

        if(objResultDTO.getSenhaCartao().equals(senha)) {
            objDTO.setValor(valorTransacao);


            //Calcular diferença de saldo. Ver no Autorizador
            validaTransacao(objDTO);

            if(objResultDTO.getValor() > valorTransacao) {
                objResultDTO.setValor(objResultDTO.getValor() - valorTransacao);

                return repository.save(objResultDTO);
            }else{
                throw new ValidatorException(String.valueOf(MensagemErro.SALDO_INSUFICIENTE), HttpStatus.PRECONDITION_FAILED);
            }
        }else {
            throw new ValidatorException(String.valueOf(MensagemErro.SENHA_INVALIDA), HttpStatus.FORBIDDEN);
        }
    }

    private void validaTransacao(CartaoDTO objDTO) {
        //O cartão existir
        Optional<Cartao> obj = repository.findByNumeroCartao(objDTO.getNumeroCartao());
        if(obj.isPresent() && !obj.get().getNumeroCartao().equals(objDTO.getNumeroCartao())) {
            throw new ValidatorException(String.valueOf(MensagemErro.CARTAO_INEXISTENTE), -2023);
        }

        //A senha do cartão for a correta
        if(obj.isPresent() && !obj.get().getSenhaCartao().equals(objDTO.getSenhaCartao())) {
            throw new ValidatorException(String.valueOf(MensagemErro.SENHA_INVALIDA), -2023);
        }

        //O cartão possuir saldo disponível para a transação
        if(obj.isPresent() && obj.get().getValor() < objDTO.getValor()) {
            throw new ValidatorException(String.valueOf(MensagemErro.SALDO_INSUFICIENTE), -2023);
        }
    }
}
