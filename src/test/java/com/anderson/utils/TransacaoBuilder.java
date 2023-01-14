package com.anderson.utils;

import com.anderson.dto.Transacao;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@NoArgsConstructor
public class TransacaoBuilder {

    public static Transacao novaTransacaoPadrao() {
        return Transacao.builder()
                .numeroCartao("1149873445634233")
                .senhaCartao("1234")
                .valor(BigDecimal.valueOf(500))
                .build();
    }

    public static Transacao novaTransacaoSenhaIncorreta() {
        return Transacao.builder()
                .numeroCartao("1149873445634233")
                .senhaCartao("5555")
                .valor(BigDecimal.valueOf(500))
                .build();
    }

    public static Transacao novaTransacaoCartaoInexistente() {
        return Transacao.builder()
                .numeroCartao("4444444444444444")
                .senhaCartao("4444")
                .valor(BigDecimal.valueOf(54.33))
                .build();
    }

    public static Transacao novaTransacaoSaldoInsuficiente() {
        return Transacao.builder()
                .numeroCartao("1149873445634233")
                .senhaCartao("1234")
                .valor(BigDecimal.valueOf(20.34))
                .build();
    }
}
