package com.calife.autorizador.resource;

import com.calife.autorizador.domain.Cartao;
import com.calife.autorizador.domain.dtos.CartaoDTO;
import com.calife.autorizador.domain.enums.MensagemErro;
import com.calife.autorizador.resources.CartaoResource;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@SpringBootTest
public class CartaoResourceTest {
    @Autowired
    private CartaoResource cartaoResource;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /*
 * criação de um cartão
 * verificação do saldo do cartão recém-criado
 */
    @Test
    public void testCriaCartaoeVeSaldo() {
        Cartao cartao = new Cartao();
        cartao.setNumeroCartao("6549873025634501");
        cartao.setSenhaCartao("321");

        CartaoDTO newObj = new CartaoDTO(cartao);
        cartaoResource.novoCartao(newObj);
        ResponseEntity<CartaoDTO> result = cartaoResource.obterDadosCartao("6549873025634501");

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getNumeroCartao()).isEqualTo("6549873025634501");
        assertThat(result.getBody().getSenhaCartao()).isEqualTo("321");
        assertThat(result.getBody().getValor()).isEqualTo(500.0);
    }

/*
 * realização de diversas transações, verificando-se o saldo em seguida, até que o sistema retorne
 *   informação de saldo insuficiente
 */
    @Test
    public void testCriaCartaoeUsaAteSaldoInsuficiente() {
        Cartao cartao = new Cartao();
        cartao.setNumeroCartao("4549873025634501");
        cartao.setSenhaCartao("321");

        CartaoDTO newObj = new CartaoDTO(cartao);
        cartaoResource.novoCartao(newObj);
        ResponseEntity<CartaoDTO> result = cartaoResource.obterDadosCartao("4549873025634501");

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getNumeroCartao()).isEqualTo("4549873025634501");
        assertThat(result.getBody().getSenhaCartao()).isEqualTo("321");
        assertThat(result.getBody().getValor()).isEqualTo(500.0);

        CartaoDTO cartaoNovo = result.getBody();
        cartaoNovo.setValor(150.0);

        cartaoResource.transacao(cartaoNovo.getNumeroCartao(), cartaoNovo);
        ResponseEntity<CartaoDTO> resultNovo1 = cartaoResource.obterDadosCartao("4549873025634501");
        assertThat(resultNovo1.getBody().getValor()).isEqualTo(350.0);

        cartaoNovo.setValor(250.0);

        cartaoResource.transacao(cartaoNovo.getNumeroCartao(), cartaoNovo);
        ResponseEntity<CartaoDTO> resultNovo2 = cartaoResource.obterDadosCartao("4549873025634501");
        assertThat(resultNovo2.getBody().getValor()).isEqualTo(100.0);

        cartaoNovo.setValor(101.0);

        try {
            cartaoResource.transacao(cartaoNovo.getNumeroCartao(), cartaoNovo);
        } catch (RuntimeException e) {
            assertEquals(String.valueOf(MensagemErro.SALDO_INSUFICIENTE), e.getMessage());
        }
        ResponseEntity<CartaoDTO> resultNovo3 = cartaoResource.obterDadosCartao("4549873025634501");
        assertThat(resultNovo3.getBody().getValor()).isEqualTo(100.0);
    }
    /*
     * realização de uma transação com senha inválida
     */
    @Test
    public void testCriaCartaoeUsaComSenhaInvalida() {
        Cartao cartao = new Cartao();
        cartao.setNumeroCartao("3549873025634501");
        cartao.setSenhaCartao("321");

        CartaoDTO newObj = new CartaoDTO(cartao);
        cartaoResource.novoCartao(newObj);
        ResponseEntity<CartaoDTO> result = cartaoResource.obterDadosCartao("3549873025634501");

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getNumeroCartao()).isEqualTo("3549873025634501");
        assertThat(result.getBody().getSenhaCartao()).isEqualTo("321");
        assertThat(result.getBody().getValor()).isEqualTo(500.0);

        CartaoDTO cartaoNovo = result.getBody();
        cartaoNovo.setSenhaCartao("123");
        cartaoNovo.setValor(150.0);

        try {
            cartaoResource.transacao(cartaoNovo.getNumeroCartao(), cartaoNovo);
        } catch (RuntimeException e) {
            assertEquals(String.valueOf(MensagemErro.SENHA_INVALIDA), e.getMessage());
        }
    }
    /*
     * realização de uma transação com cartão inexistente
     */
    @Test
    public void testCriaCartaoeUsaComCartaoInexistente() {
        Cartao cartao = new Cartao();
        cartao.setNumeroCartao("2549873025634501");
        cartao.setSenhaCartao("321");

        CartaoDTO newObj = new CartaoDTO(cartao);
        cartaoResource.novoCartao(newObj);
        ResponseEntity<CartaoDTO> result = cartaoResource.obterDadosCartao("2549873025634501");

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getNumeroCartao()).isEqualTo("2549873025634501");
        assertThat(result.getBody().getSenhaCartao()).isEqualTo("321");
        assertThat(result.getBody().getValor()).isEqualTo(500.0);

        CartaoDTO cartaoNovo = result.getBody();
        cartaoNovo.setNumeroCartao("1549873025634501");
        cartaoNovo.setValor(150.0);

        try {
            cartaoResource.transacao(cartaoNovo.getNumeroCartao(), cartaoNovo);
        } catch (RuntimeException e) {
            assertEquals(String.valueOf(MensagemErro.CARTAO_INEXISTENTE), e.getMessage());
        }
    }
}