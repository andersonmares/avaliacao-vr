package com.anderson.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
@Getter
public class CartaoDuplicadoException extends RuntimeException {


    private final String numeroCartao;

    private final String senha;

    public CartaoDuplicadoException (String numeroCartao, String senha) {
        super();
        this.numeroCartao = numeroCartao;
        this.senha = senha;
    }

}
