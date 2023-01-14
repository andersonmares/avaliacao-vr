package com.anderson.service;

import com.anderson.exception.CartaoDuplicadoException;
import com.anderson.exception.CartaoInexistenteSaldoException;
import com.anderson.exception.CartaoInvalidoException;
import com.anderson.domain.Cartao;
import com.anderson.repository.CartaoRepository;
import com.anderson.dto.CartaoResponse;
import com.anderson.dto.CriarCartao;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
@Log4j2
@Transactional
@NoArgsConstructor
public class CartaoService {

    @Autowired
    private CartaoRepository cartaoRepository;

    public CartaoResponse criarCartao(CriarCartao cartao) {
        log.info("Criando um Novo Cartão");

        verificaSeCartaoTemApenasNumeros(cartao.getNumeroCartao());

        verificaSeCartaoDuplicado(cartao);

        Cartao novoCartao = new Cartao(cartao);
        cartaoRepository.save(novoCartao);

        log.info("Cartão {} criado com sucesso!", cartao.getNumeroCartao());
        return new CartaoResponse(novoCartao);
    }

    public BigDecimal obterSaldoCartao(String numeroCartao) {
        log.info("Saldo do Cartão");

        return cartaoRepository.findByNumeroCartao(numeroCartao)
                .map(Cartao::getSaldo).orElseThrow(CartaoInexistenteSaldoException::new);
    }

    private void verificaSeCartaoDuplicado(CriarCartao cartao) {
        cartaoRepository.findByNumeroCartao(cartao.getNumeroCartao())
                .ifPresent(c -> {
                    log.error("O cartão {} já existe!", cartao.getNumeroCartao());
                    throw new CartaoDuplicadoException(cartao.getNumeroCartao(), cartao.getSenha());
                });
    }

    private void verificaSeCartaoTemApenasNumeros(String numeroCartao) {
        if(!numeroCartao.matches("^\\d+$"))
            throw new CartaoInvalidoException();
    }
}
