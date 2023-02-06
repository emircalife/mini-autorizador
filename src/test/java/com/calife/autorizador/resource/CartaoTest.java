package com.calife.autorizador.resource;

import com.calife.autorizador.domain.Cartao;
import com.calife.autorizador.domain.dtos.CartaoDTO;
import com.calife.autorizador.repositories.CartaoRepository;
import com.calife.autorizador.services.CartaoService;
import com.calife.autorizador.services.CartaoServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartaoTest {
    @InjectMocks
    private CartaoServiceImpl cartaoService;
    @Mock
    private CartaoRepository cartaoRepository;
    @Test
    public void testCriaCartao() {
        CartaoDTO cartaoDTO = new CartaoDTO();
        cartaoDTO.setNumeroCartao("6549873025634501");
        cartaoDTO.setSenhaCartao("1234");

        Cartao cartao = new Cartao(cartaoDTO);
        Cartao cartaoRetorno = new Cartao();
        when(cartaoRepository.save(cartao)).thenReturn(cartaoRetorno);
        Integer qtdeCartao = cartaoRepository.findAll().size();
        assertEquals(1, qtdeCartao);

        Cartao cartaoCriado = cartaoService.create(cartaoDTO);

        assertThat(cartaoCriado.getNumeroCartao()).isNotNull();
        assertThat(cartaoCriado.getNumeroCartao()).isEqualTo("6549873025634501");
        assertThat(cartaoCriado.getValor()).isEqualTo(500.0);
    }
}
