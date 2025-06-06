package com.reslink.demo.controller;

import com.reslink.demo.dto.useralerts.AlertCreateRequestDto;
import com.reslink.demo.dto.useralerts.AlertResponseDto; // IMPORTAR
import com.reslink.demo.dto.useralerts.AlertVoteRequestDto;
import com.reslink.demo.service.UserAlertService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-alerts")
public class UserAlertController {

    @Autowired
    private UserAlertService userAlertService;

    @PostMapping
    // Mude o tipo de retorno
    public ResponseEntity<AlertResponseDto> createAlert(
            @Valid @RequestBody AlertCreateRequestDto alertDto,
            @AuthenticationPrincipal UserDetails userDetails) {

        AlertResponseDto createdAlertDto = userAlertService.createAlert(alertDto, userDetails);
        return ResponseEntity.ok(createdAlertDto);
    }

    @PostMapping("/{alertId}/vote")
    // Mude o tipo de retorno
    public ResponseEntity<AlertResponseDto> voteOnAlert(
            @PathVariable Long alertId,
            @Valid @RequestBody AlertVoteRequestDto voteDto,
            @AuthenticationPrincipal UserDetails userDetails) {

        AlertResponseDto updatedAlertDto = userAlertService.voteOnAlert(alertId, voteDto.getVoteType(), userDetails);
        return ResponseEntity.ok(updatedAlertDto);
    }
}