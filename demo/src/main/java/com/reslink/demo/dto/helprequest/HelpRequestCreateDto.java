package com.reslink.demo.dto.helprequest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class HelpRequestCreateDto {

    @NotBlank(message = "A descrição é obrigatória.")
    @Size(min = 10, max = 500, message = "A descrição deve ter entre 10 e 500 caracteres.")
    private String description;
}