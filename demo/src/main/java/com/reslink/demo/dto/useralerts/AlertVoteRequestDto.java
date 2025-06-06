package com.reslink.demo.dto.useralerts;

import com.reslink.demo.model.enums.VoteType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AlertVoteRequestDto {
    @NotNull
    private VoteType voteType; // Recebe diretamente "CONFIRMATION" ou "DENIAL"
}