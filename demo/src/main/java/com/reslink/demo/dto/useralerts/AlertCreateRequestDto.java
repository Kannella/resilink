package com.reslink.demo.dto.useralerts;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AlertCreateRequestDto {
    @NotBlank
    private String description;
    @NotBlank
    private String category;
    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;
}