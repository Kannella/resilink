package com.reslink.demo.controller;

import com.reslink.demo.dto.LoginRequestDto;
import com.reslink.demo.dto.LoginResponseDto;
import com.reslink.demo.model.User;
import com.reslink.demo.repository.UserRepository;
import com.reslink.demo.service.LoginService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private LoginService loginService;

    // Endpoint para o processo de login
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequest) {
        LoginResponseDto response = loginService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    // --- Endpoint auxiliar para criar um usuário para teste ---
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody LoginRequestDto request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email já cadastrado.");
        }
        User newUser = new User();
        newUser.setEmail(request.getEmail());
        newUser.setPassword(request.getPassword()); // Novamente, use hash em produção!
        userRepository.save(newUser);
        return ResponseEntity.ok("Usuário registrado com sucesso: " + newUser.getEmail());
    }
}