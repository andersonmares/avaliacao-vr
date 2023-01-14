package com.anderson.contract;

import com.anderson.utils.StatusTransacoesEnum;
import com.anderson.dto.CartaoResponse;
import com.anderson.dto.Transacao;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Tag(name = "2. Transações", description = "Realizar transações com o cartão")
@RequestMapping(value = "/transacoes")
public interface TransacoesContract {

    @Operation(summary = "Realizar uma transação")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = CartaoResponse.class))),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(schema = @Schema(implementation = StatusTransacoesEnum.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping
    ResponseEntity<StatusTransacoesEnum> executarTransacao(@RequestBody @Valid Transacao transacao);
}
