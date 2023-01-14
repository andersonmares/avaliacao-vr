package com.anderson.contract;

import com.anderson.dto.CartaoResponse;
import com.anderson.dto.CriarCartao;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

@Tag(name = "Cartão", description = "Gerenciamento dos Cartões.")
@RequestMapping(value = "/cartoes")
public interface CartaoContract {

    @Operation(summary = "Criar Novo Cartão")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "201",
                    description = "Created", content = @Content(schema = @Schema(implementation = CartaoResponse.class))),
            @ApiResponse(responseCode = "422",
                    description = "Unprocessable Entity", content = @Content(schema = @Schema(implementation = CartaoResponse.class))),
    })
    @PostMapping
    ResponseEntity<CartaoResponse> criarCartao(@RequestBody @Valid CriarCartao cartao);

    @Operation(summary = "Consultar Saldo do Cartão")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200",
                    description = "OK", content = @Content(schema = @Schema(implementation = BigDecimal.class))),
            @ApiResponse(responseCode = "404",
                    description = "Not Found"),
            @ApiResponse(responseCode = "500",
                    description = "Internal Server Error")
    })

    @GetMapping(value = "/{numeroCartao}")
    ResponseEntity<BigDecimal> obterSaldoCartao(@PathVariable String numeroCartao);


}
