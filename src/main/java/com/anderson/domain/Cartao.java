package com.anderson.domain;

import com.anderson.dto.CriarCartao;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cartao")
public class Cartao {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;

    @NotNull
    @Column(name = "numeroCartao", length = 16)
    private String numeroCartao;

    @NotNull
    @Column(name = "senha")
    private String senha;

    @NotNull
    @Setter
    @Column(name = "saldo")
    private BigDecimal saldo;

    public Cartao(CriarCartao cartao) {
        this.numeroCartao = cartao.getNumeroCartao();
        this.senha = cartao.getSenha();
        this.saldo = BigDecimal.valueOf(500.00).setScale(2, RoundingMode.DOWN);
    }
}
