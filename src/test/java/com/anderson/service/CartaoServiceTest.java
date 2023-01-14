package com.anderson.service;

import com.anderson.exception.CartaoDuplicadoException;
import com.anderson.exception.CartaoInexistenteSaldoException;
import com.anderson.exception.CartaoInvalidoException;
import com.anderson.domain.Cartao;
import com.anderson.repository.CartaoRepository;
import com.anderson.dto.CriarCartao;
import com.anderson.utils.CartaoBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static com.anderson.utils.Constants.NUMERO_CARTAO_INEXISTENTE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CartaoServiceTest {

    @InjectMocks
    private CartaoService cartaoService;

    @Mock
    private CartaoRepository cartaoRepository;

    @Captor
    ArgumentCaptor<Cartao> cartaoCaptor;

    private final Cartao cartaoPadraoEntidade = CartaoBuilder.cartaoPadraoEntidade();

    private final CriarCartao novoCartaoComAlfaNumerico = CartaoBuilder.novoCartaoComAlfaNumerico();

    private final CriarCartao novoCartaoCorreto = CartaoBuilder.novoCartaoCorreto();

    @Test
    void quandoAlfanumericoNoNumeroCartaoThrowsCartaoInvalidoException() {
        assertThrows(CartaoInvalidoException.class,
                () -> cartaoService.criarCartao(novoCartaoComAlfaNumerico));
    }

    @Test
    void quandoCartaoExistirThrowsCartaoDuplicadoException() {
        when(cartaoRepository.findByNumeroCartao(any(String.class))).thenReturn(Optional.of(cartaoPadraoEntidade));

        assertThrows(CartaoDuplicadoException.class,
                () -> cartaoService.criarCartao(novoCartaoCorreto));
    }

    @Test
    void quandoCartaoValidoSalvarCartao() {
        cartaoService.criarCartao(novoCartaoCorreto);
        verify(cartaoRepository).save(cartaoCaptor.capture());
    }

    @Test
    void quandoCartaoNaoExistisThrowCartaoInexistenteException() {
        when(cartaoRepository.findByNumeroCartao(any(String.class))).thenReturn(Optional.empty());

        assertThrows(CartaoInexistenteSaldoException.class,
                () -> cartaoService.obterSaldoCartao(NUMERO_CARTAO_INEXISTENTE));
    }

    @Test
    void quandoCartaoExistirRetornarSaldoDoCartao() {
        when(cartaoRepository.findByNumeroCartao(any(String.class))).thenReturn(Optional.of(cartaoPadraoEntidade));
        assertEquals(BigDecimal.valueOf(500), cartaoService.obterSaldoCartao(cartaoPadraoEntidade.getNumeroCartao()));
    }
}
