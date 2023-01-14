package com.anderson.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class CartaoInvalidoException extends RuntimeException {

    public CartaoInvalidoException() {
        super("Cartão Inválido!");
    }
}
