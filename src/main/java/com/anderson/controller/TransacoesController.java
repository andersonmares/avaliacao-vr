package com.anderson.controller;

import com.anderson.contract.TransacoesContract;
import com.anderson.utils.StatusTransacoesEnum;
import com.anderson.exception.CartaoInexistenteTransacaoException;
import com.anderson.exception.SaldoInsuficienteException;
import com.anderson.exception.SenhaInvalidaException;
import com.anderson.dto.Transacao;
import com.anderson.service.TransacoesService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Log4j2
@RestController
public class TransacoesController implements TransacoesContract {

    @Autowired
    private TransacoesService transacoesService;

    @Override
    public ResponseEntity<StatusTransacoesEnum> executarTransacao(Transacao transacao) {

        try {
            transacoesService.realizarTransacao(transacao);
            return new ResponseEntity<>(StatusTransacoesEnum.OK, OK);

        } catch (CartaoInexistenteTransacaoException e) {
            log.error("Cartão Inválido!");
            return new ResponseEntity<>(StatusTransacoesEnum.CARTAO_INEXISTENTE, UNPROCESSABLE_ENTITY);

        } catch (SenhaInvalidaException e) {
            log.error("Senha Inválida!");
            return new ResponseEntity<>(StatusTransacoesEnum.SENHA_INVALIDA, UNPROCESSABLE_ENTITY);

        } catch (SaldoInsuficienteException e) {
            log.error("Saldo Insuficiente!");
            return new ResponseEntity<>(StatusTransacoesEnum.SALDO_INSUFICIENTE, UNPROCESSABLE_ENTITY);
        }
    }
}
