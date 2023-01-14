package com.anderson.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.anderson.exception.CartaoInexistenteTransacaoException;
import com.anderson.exception.SaldoInsuficienteException;
import com.anderson.exception.SenhaInvalidaException;
import com.anderson.dto.Transacao;
import com.anderson.service.TransacoesService;
import com.anderson.utils.TransacaoBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TransacoesController.class)
@AutoConfigureMockMvc
class TransacoesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TransacoesService transacoesService;

    private final Transacao novaTransacaoPadrao = TransacaoBuilder.novaTransacaoPadrao();

    private final Transacao novaTransacaoCartaoInexistente = TransacaoBuilder.novaTransacaoCartaoInexistente();

    private final Transacao novaTransacaoSenhaIncorreta = TransacaoBuilder.novaTransacaoSenhaIncorreta();

    private final Transacao novaTransacaoSaldoInsuficiente = TransacaoBuilder.novaTransacaoSaldoInsuficiente();

    private final static String BASE_URL = "/transacoes";

    private final static String APPLICATION_JSON = "application/json";

    @Test
    void quandoTransacaoValidaRetornaHttpStatus201() throws Exception {
        mockMvc.perform(post(BASE_URL)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(novaTransacaoPadrao)))
                .andExpect(status().isOk());
    }

    @Test
    void quandoTransacaoComCartaoInexistenteRetornaHttpStatus422() throws Exception {
        doThrow(CartaoInexistenteTransacaoException.class).when(transacoesService).realizarTransacao(novaTransacaoCartaoInexistente);

        mockMvc.perform(post(BASE_URL)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(novaTransacaoCartaoInexistente)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void quandoTransacaoComSenhaIncorretaRetornaHttpStatus422() throws Exception {
        doThrow(SenhaInvalidaException.class).when(transacoesService).realizarTransacao(novaTransacaoSenhaIncorreta);

        mockMvc.perform(post(BASE_URL)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(novaTransacaoSenhaIncorreta)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void quandoTransacaoComSaldoInsuficienteRetornaHttpStatus422() throws Exception {
        doThrow(SaldoInsuficienteException.class).when(transacoesService).realizarTransacao(novaTransacaoSaldoInsuficiente);

        mockMvc.perform(post(BASE_URL)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(novaTransacaoSaldoInsuficiente)))
                .andExpect(status().isUnprocessableEntity());
    }
}
