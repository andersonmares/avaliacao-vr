package com.anderson.utils;

import com.anderson.domain.Cartao;
import com.anderson.dto.CriarCartao;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
@NoArgsConstructor
public class CartaoBuilder {

    public static Cartao cartaoPadraoEntidade() {

        return Cartao.builder()
                .id(UUID.randomUUID())
                .numeroCartao("1149873445634233")
                .senha("1234")
                .saldo(BigDecimal.valueOf(500))
                .build();
    }

    public static CriarCartao cartaoPadraoDuplicado() {

        return CriarCartao.builder()
                .numeroCartao("1149873445634233")
                .senha("1234")
                .build();
    }

    public static Cartao cartaoPadraoSaldoInsuficienteEntidade() {

        return Cartao.builder()
                .id(UUID.randomUUID())
                .numeroCartao("1149873445634233")
                .senha("1234")
                .saldo(BigDecimal.valueOf(20.15))
                .build();
    }

    public static CriarCartao novoCartaoCorreto() {

        return CriarCartao.builder()
                .numeroCartao("6549873885634223")
                .senha("1234")
                .build();
    }

    public static CriarCartao novoCartaoComAlfaNumerico() {

        return CriarCartao.builder()
                .numeroCartao("dd49873xx56342vv")
                .senha("1234")
                .build();
    }

}
