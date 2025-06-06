package com.reslink.demo.service;

import com.reslink.demo.dto.LoginRequestDto;
import com.reslink.demo.dto.LoginResponseDto;
import com.reslink.demo.model.User;
import com.reslink.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder; // IMPORTADO
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // INJETADO

    @Autowired private JwtService jwtService;
    @Autowired private AuthenticationManager authenticationManager; // Precisaremos disso

    public String loginAndGetToken(LoginRequestDto loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow();
        // Atualiza a localização
        user.setLastLatitude(loginRequest.getLatitude());
        user.setLastLongitude(loginRequest.getLongitude());
        userRepository.save(user);
        // Gera e retorna o token
        return jwtService.generateToken(user);
    }

    @Transactional
    public LoginResponseDto login(LoginRequestDto loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário ou senha inválidos"));

        // ATUALIZADO: Valida a senha usando o PasswordEncoder
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Usuário ou senha inválidos");
        }

        user.setLastLatitude(loginRequest.getLatitude());
        user.setLastLongitude(loginRequest.getLongitude());

        userRepository.save(user);

        return new LoginResponseDto("Login bem-sucedido e localização atualizada!", user.getEmail());
    }
}