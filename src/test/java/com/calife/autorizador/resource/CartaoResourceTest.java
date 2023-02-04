//package com.calife.autorizador.resource;
//
//import com.calife.autorizador.domain.Cartao;
//import com.calife.autorizador.domain.dtos.CartaoDTO;
//import com.calife.autorizador.repositories.CartaoRepository;
//import com.calife.autorizador.resources.CartaoResource;
//import com.calife.autorizador.services.CartaoService;
//import org.junit.Assert;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.ResponseEntity;
//
//import java.util.Optional;
//
//@ExtendWith(MockitoExtension.class)
//public class CartaoResourceTest {
//    @InjectMocks
//    private CartaoResource resource;
//    @Mock
//    private CartaoService service;
//    @Mock
//    private CartaoRepository repository;
//
//    @Test
//    public void testCriaCartao() {
//        Cartao cartaoRet = new Cartao();
//        Cartao cartao = new Cartao();
//        cartao.setNumeroCartao("6549873025634501");
//        cartao.setSenhaCartao("321");
//
//        CartaoDTO newObj = new CartaoDTO(cartao);
//
//        resource.novoCartao(newObj);
//
//        Mockito.when(service.findByNumeroCartao("6549873025634501")).thenReturn(cartaoRet);
//
//        // efetuando a chamada do resource
//        ResponseEntity<CartaoDTO> response = resource.obterDadosCartao("6549873025634501");
//
//        Assert.assertEquals(ResponseEntity.ok().build().getStatusCode(), response.getStatusCode());
//        Assert.assertEquals("6549873025634501", response.getBody().getNumeroCartao());
//        Assert.assertEquals("321", response.getBody().getSenhaCartao());
//        Assert.assertEquals(Optional.of(500.00), response.getBody().getValor());
//    }
//
//    @Test
//    public void testSaldoInsuficiente() {
//        Cartao cartaoRet = new Cartao();
//        Cartao cartao = new Cartao();
//
//        cartao.setNumeroCartao("6549873025634501");
//        cartao.setSenhaCartao("321");
//
//        CartaoDTO novoCartaoDTO = new CartaoDTO(cartao);
//
//        resource.novoCartao(novoCartaoDTO);
//
//        Mockito.when(service.findByNumeroCartao("6549873025634501")).thenReturn(cartaoRet);
//        Mockito.when(service.transacao("6549873025634501","321", 100.00)).thenReturn(cartaoRet);
//        Mockito.when(service.getSaldo("6549873025634501")).thenReturn(cartaoRet);
//        Assert.assertEquals(Optional.of(400.00), cartaoRet.getValor());
//
//        Mockito.when(service.transacao("6549873025634501","321", 100.00)).thenReturn(cartaoRet);
//        Assert.assertEquals(Optional.of(300.00), cartaoRet.getValor());
//
//        Mockito.when(service.transacao("6549873025634501","321", 200.00)).thenReturn(cartaoRet);
//        Assert.assertEquals(Optional.of(100.00), cartaoRet.getValor());
//
//        Mockito.when(service.transacao("6549873025634501","321", 120.00)).thenReturn(cartaoRet);
//
//        ResponseEntity<CartaoDTO> response = resource.obterDadosCartao("6549873025634501");
//
//        Assert.assertEquals(ResponseEntity.ok().build().getStatusCode(), response.getStatusCode());
//        Assert.assertEquals("6549873025634501", response.getBody().getNumeroCartao());
//        Assert.assertEquals("321", response.getBody().getSenhaCartao());
//        Assert.assertEquals(Optional.of(100.00), response.getBody().getValor());
//    }
//}