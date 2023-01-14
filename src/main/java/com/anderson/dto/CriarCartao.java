package com.anderson.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CriarCartao {

    @NotNull
    @Size(min = 16, message = "O cartão deve ter 16 números!")
    @Size(max = 16, message = "O cartão deve ter 16 números!")
    @Schema(description = "Número do Cartão", example = "6549873025634501", required = true)
    private String numeroCartao;

    @NotNull
    @NotBlank
    @Schema(description = "Senha do Cartão", example = "1234", required = true)
    private String senha;

}
