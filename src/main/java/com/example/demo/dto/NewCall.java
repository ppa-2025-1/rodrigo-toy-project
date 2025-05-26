package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NewCall(

        @NotNull(message = "A ação é obrigatória")
        @NotBlank(message = "A ação não pode ser vazia")
        String action,

        @NotNull(message = "O objeto é obrigatório")
        @NotBlank(message = "O objeto não pode ser vazio")
        String object,

        String details,

        @NotNull(message = "O ID do usuário é obrigatório")
        Integer userId

) {
}
