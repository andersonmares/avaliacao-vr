package com.anderson.service;

import com.anderson.exception.CartaoInexistenteTransacaoException;
import com.anderson.exception.SaldoInsuficienteException;
import com.anderson.exception.SenhaInvalidaException;
import com.anderson.domain.Cartao;
import com.anderson.repository.CartaoRepository;
import com.anderson.dto.Transacao;
import com.anderson.utils.CartaoBuilder;
import com.anderson.utils.TransacaoBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class TransacoesServiceTest {

    @InjectMocks
    private TransacoesService transacoesService;
    @Mock
    private CartaoRepository cartaoRepository;

    private final Cartao cartaoPadraoEntidade = CartaoBuilder.cartaoPadraoEntidade();

    private final Cartao cartaoPadraoSaldoInsuficienteEntidade = CartaoBuilder.cartaoPadraoSaldoInsuficienteEntidade();

    private final Transacao novaTransacaoSenhaIncorreta = TransacaoBuilder.novaTransacaoSenhaIncorreta();

    private final Transacao novaTransacaoPadrao = TransacaoBuilder.novaTransacaoPadrao();


    @Test
    void quandoSenhaIncorretaThrowsSenhaIncorretaException() {
        when(cartaoRepository.findByNumeroCartao(any(String.class))).thenReturn(Optional.ofNullable(cartaoPadraoEntidade));
        assertThrows(SenhaInvalidaException.class,
                () -> transacoesService.realizarTransacao(novaTransacaoSenhaIncorreta));
    }

    @Test
    void quandoCartaoSemSaldoThrowsSaldoInsuficienteException() {
        when(cartaoRepository.findByNumeroCartao(any(String.class))).thenReturn(Optional.ofNullable(cartaoPadraoSaldoInsuficienteEntidade));
        assertThrows(SaldoInsuficienteException.class,
                () -> transacoesService.realizarTransacao(novaTransacaoPadrao));
    }

    @Test
    void quandoCartaoNaoExisteThrowsCartaoInexistenteException() {
        when(cartaoRepository.findByNumeroCartao(any(String.class))).thenReturn(Optional.empty());
        assertThrows(CartaoInexistenteTransacaoException.class,
                () -> transacoesService.realizarTransacao(novaTransacaoPadrao));
    }

    @Test
    void quandoTransacaoValidaEfetuarTransacao() {
        when(cartaoRepository.findByNumeroCartao(any(String.class))).thenReturn(Optional.ofNullable(cartaoPadraoEntidade));

        assertNotNull(cartaoPadraoEntidade);
        transacoesService.realizarTransacao(novaTransacaoPadrao);

        verify(cartaoRepository).save(cartaoPadraoEntidade);
    }
}
