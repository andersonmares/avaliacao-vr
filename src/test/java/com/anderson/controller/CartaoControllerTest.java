package com.anderson.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.anderson.exception.CartaoDuplicadoException;
import com.anderson.exception.CartaoInexistenteSaldoException;
import com.anderson.exception.CartaoInvalidoException;
import com.anderson.dto.CriarCartao;
import com.anderson.service.CartaoService;
import com.anderson.utils.CartaoBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;


import static com.anderson.utils.Constants.APPLICATION_JSON;
import static com.anderson.utils.Constants.BASE_URL;
import static com.anderson.utils.Constants.NUMERO_CARTAO_INEXISTENTE;
import static com.anderson.utils.Constants.NUMERO_CARTAO_VALIDO;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CartaoController.class)
@AutoConfigureMockMvc
class CartaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CartaoService cartaoService;

    private final CriarCartao cartaoPadraoDuplicado = CartaoBuilder.cartaoPadraoDuplicado();

    private final CriarCartao novoCartaoCorreto = CartaoBuilder.novoCartaoCorreto();

    private final CriarCartao novoCartaoComAlfaNumerico = CartaoBuilder.novoCartaoComAlfaNumerico();

    @Test
    void criaCartaoComSucessoRetornaHttpStatusCode201() throws Exception {
        mockMvc.perform(post(BASE_URL)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(novoCartaoCorreto)))
                .andExpect(status().isCreated());
    }

    @Test
    void criaCartaoDuplicadoRetornaHttpStatusCode422() throws Exception {
        when(cartaoService.criarCartao(cartaoPadraoDuplicado)).thenThrow(CartaoDuplicadoException.class);

        mockMvc.perform(post(BASE_URL)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartaoPadraoDuplicado)))
                        .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void quandoCartaoCriadoComAlfanumericoRetornaHttpStatusCode422() throws Exception {
        when(cartaoService.criarCartao(novoCartaoComAlfaNumerico)).thenThrow(CartaoInvalidoException.class);

        mockMvc.perform(post(BASE_URL)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(novoCartaoComAlfaNumerico)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void quandoCartaoValidoRetornaSaldoCartaoRetornaHttpStatusCode200() throws Exception {
        when(cartaoService.obterSaldoCartao(NUMERO_CARTAO_VALIDO)).thenReturn(BigDecimal.valueOf(500));

        mockMvc.perform(get(BASE_URL + "/{numeroCartao}", NUMERO_CARTAO_VALIDO)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void quandoCartaoInexistenteAoObterSaldoRetornaHttpStatus404() throws Exception {
        when(cartaoService.obterSaldoCartao(NUMERO_CARTAO_INEXISTENTE)).thenThrow(CartaoInexistenteSaldoException.class);

        mockMvc.perform(get(BASE_URL + "/{numeroCartao}", NUMERO_CARTAO_INEXISTENTE)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
