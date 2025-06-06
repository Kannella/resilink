package com.reslink.demo.controller;

import com.reslink.demo.dto.LoginRequestDto;
import com.reslink.demo.dto.LoginResponseDto;
import com.reslink.demo.dto.RegistrationRequestDto;
import com.reslink.demo.model.User;
import com.reslink.demo.service.LoginService;
import com.reslink.demo.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth") // Rota base para autenticação
public class AuthController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private RegistrationService registrationService;

    // --- Endpoint de Registro ---
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegistrationRequestDto registrationRequest) {
        try {
            User newUser = registrationService.registerNewUser(registrationRequest);
            String responseMessage = "Usuário registrado com sucesso! ID: " + newUser.getId();
            return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // --- Endpoint de Login ---
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequest) {
        try {
            LoginResponseDto response = loginService.login(loginRequest);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Retorna 401 para falha de login
        }
    }
}