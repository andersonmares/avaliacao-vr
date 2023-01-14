package com.anderson.dto;

import com.anderson.domain.Cartao;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@AllArgsConstructor
public class CartaoResponse {

    @Schema(description = "Senha do Cartão")
    private String senha;

    @Schema(description = "Número do Cartão")
    private String numeroCartao;

    public CartaoResponse(Cartao cartao) {
        this.senha = cartao.getSenha();
        this.numeroCartao = cartao.getNumeroCartao();
    }

}
