package com.anderson.controller;

import com.anderson.contract.CartaoContract;
import com.anderson.exception.CartaoDuplicadoException;
import com.anderson.exception.CartaoInexistenteSaldoException;
import com.anderson.exception.CartaoInvalidoException;
import com.anderson.dto.CartaoResponse;
import com.anderson.dto.CriarCartao;
import com.anderson.service.CartaoService;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.*;

@Log4j2
@NoArgsConstructor
@RestController
public class CartaoController implements CartaoContract {

    @Autowired
    private CartaoService cartaoService;

    @Override
    public ResponseEntity<CartaoResponse> criarCartao(CriarCartao cartao) {
        CartaoResponse response;
        try {
            response = cartaoService.criarCartao(cartao);
            return new ResponseEntity<>(response, CREATED);
        } catch (CartaoDuplicadoException e) {
            response = new CartaoResponse(e.getSenha(), e.getNumeroCartao());
            log.error("Cartão Duplicado!");
            return new ResponseEntity<>(response, UNPROCESSABLE_ENTITY);

        } catch (CartaoInvalidoException e) {
            log.error("Cartão Inválido!");
            return new ResponseEntity<>(null, UNPROCESSABLE_ENTITY);
        }
    }

    @Override
    public ResponseEntity<BigDecimal> obterSaldoCartao(String numeroCartao) {
        try {
            BigDecimal response = cartaoService.obterSaldoCartao(numeroCartao);
            return new ResponseEntity<>(response, OK);

        } catch (CartaoInexistenteSaldoException e) {
            log.error("Cartão Inexistente!");
            return new ResponseEntity<>(null, NOT_FOUND);
        }
    }
}
