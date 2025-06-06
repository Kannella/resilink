package com.reslink.demo.controller;

import com.reslink.demo.dto.AuthResponseDto;
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

    // Mude a assinatura do método de login
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequest) {
        try {
            String token = loginService.loginAndGetToken(loginRequest); // Método do serviço vai mudar
            return ResponseEntity.ok(new AuthResponseDto(token));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    // O método de registro pode permanecer o mesmo, mas vamos usar o AuthResponseDto também
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> registerUser(@Valid @RequestBody RegistrationRequestDto request) {
        try {
            String token = registrationService.registerNewUserAndGetToken(request); // Método do serviço vai mudar
            return ResponseEntity.status(HttpStatus.CREATED).body(new AuthResponseDto(token));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}