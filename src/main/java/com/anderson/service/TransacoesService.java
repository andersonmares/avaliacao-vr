package com.anderson.service;

import com.anderson.exception.CartaoInexistenteTransacaoException;
import com.anderson.exception.SaldoInsuficienteException;
import com.anderson.exception.SenhaInvalidaException;
import com.anderson.domain.Cartao;
import com.anderson.repository.CartaoRepository;
import com.anderson.dto.Transacao;
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
public class TransacoesService {

    @Autowired
    private CartaoRepository cartaoRepository;

    /*
    * threadLock tem o papel de ser o monitor do lock que é usado como parâmetro para escopos que utilizam synchronized, dessa forma evitando
    * que outra thread acesse aquele escopo de execução simultaneamente. Também poderia ser utilizado o this no lugar do Object, porém
    * o this é acessível fora da classe, e o Object não, por ser declarado como final. Isso favorece a proteção contra um ataque hacker
    * de DoS (negação de serviço) em que o lock de execução da thread é mantido eternamente impedindo o fluxo da aplicação (deadlock).
    * */
    private final Object threadLock = new Object();

    public void realizarTransacao(Transacao transacao) {
        log.info("Iniciando Transação...");

        cartaoRepository.findByNumeroCartao(transacao.getNumeroCartao())
                .ifPresentOrElse(cartao -> {
                    log.info("Cartão localizado {}", cartao.getNumeroCartao());

                    verificaSenhaCartao(transacao.getSenhaCartao(), cartao.getSenha());

                    verificaSaldoCartao(cartao.getSaldo(), transacao.getValor());

                    efetuaTransacao(cartao, transacao);

                }, () -> {
                    log.error("Cartão Inexistente!");
                    throw new CartaoInexistenteTransacaoException();
                });
    }

    private void verificaSenhaCartao(String senhaTransacao, String senhaCartao) {
        if (!senhaTransacao.equals(senhaCartao))
            throw new SenhaInvalidaException();
        log.info("Senha correta");
    }

    private void verificaSaldoCartao(BigDecimal saldoCartao, BigDecimal valorTransacao) {
        if ((saldoCartao.subtract(valorTransacao)).signum() == -1)
            throw new SaldoInsuficienteException();
        log.info("Saldo suficiente");
    }

    private void efetuaTransacao(Cartao cartao, Transacao transacao) {
        /*
         * O synchronized faz com que apenas uma thread acesse seu escopo por vez evitando duas transações ao mesmo tempo
         * para aquela instrução.
         * */
        synchronized (threadLock) {
            cartao.setSaldo(cartao.getSaldo().subtract(transacao.getValor()));
            cartaoRepository.save(cartao);
            log.info("Transação efetuada");
        }
    }
}
