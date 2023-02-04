package com.calife.autorizador.resources;

import java.net.URI;
import java.util.Optional;

import com.calife.autorizador.domain.Cartao;
import com.calife.autorizador.domain.dtos.CartaoDTO;
import com.calife.autorizador.services.CartaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/cartoes")
public class CartaoResource {
    @Autowired
    private CartaoService service;

    public CartaoResource(CartaoService service) {
        this.service = service;
    }

    @GetMapping(value = "/{numeroCartao}")
    public ResponseEntity<CartaoDTO> obterDadosCartao(@PathVariable String numero){
        Cartao obj = service.findByNumeroCartao(numero);
        return ResponseEntity.ok().body(new CartaoDTO(obj));
    }

    @PutMapping(value = "/{numeroCartao}")
    public ResponseEntity<CartaoDTO> transacao(@PathVariable String numeroCartao, @RequestBody CartaoDTO objDTO){
        Cartao oldObj = service.transacao(numeroCartao, objDTO.getSenhaCartao(), objDTO.getValor());
        return ResponseEntity.ok().body(new CartaoDTO(oldObj));
    }

    @PostMapping
    public ResponseEntity<CartaoDTO> novoCartao(@Valid @RequestBody CartaoDTO cartaoDTO){
        Cartao cartao = service.create(cartaoDTO);

        return ResponseEntity.ok().body(new CartaoDTO(cartao));
    }
}
