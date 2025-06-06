package com.reslink.demo.dto.helprequest;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class HelpRequestResponseDto {

    private Long id;
    private String description;
    private LocalDateTime createdAt;

    // Incluímos o email para o "botão de contato" que o frontend irá criar.
    private String requesterEmail;
}