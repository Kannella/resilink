package com.reslink.demo.dto.useralerts;

import com.reslink.demo.model.enums.AlertStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlertResponseDto {

    private Long id;
    private String description;
    private String category;
    private Double latitude;
    private Double longitude;
    private AlertStatus status;
    private LocalDateTime createdAt;

    // Informações úteis que vamos calcular
    private String submitterEmail;
    private long confirmationCount;
    private long denialCount;
}