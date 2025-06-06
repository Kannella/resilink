package com.reslink.demo.controller;

import com.reslink.demo.dto.helprequest.HelpRequestCreateDto;
import com.reslink.demo.dto.helprequest.HelpRequestResponseDto;
import com.reslink.demo.service.HelpRequestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/help-requests")
public class HelpRequestController {

    @Autowired
    private HelpRequestService helpRequestService;

    @PostMapping
    public ResponseEntity<HelpRequestResponseDto> createHelpRequest(
            @Valid @RequestBody HelpRequestCreateDto createDto,
            @AuthenticationPrincipal UserDetails userDetails) {

        HelpRequestResponseDto responseDto = helpRequestService.createRequest(createDto, userDetails);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<HelpRequestResponseDto>> getAllHelpRequests() {
        List<HelpRequestResponseDto> requests = helpRequestService.getAllRequests();
        return ResponseEntity.ok(requests);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHelpRequest(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {

        helpRequestService.deleteRequest(id, userDetails);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content, ideal para delete
    }
}