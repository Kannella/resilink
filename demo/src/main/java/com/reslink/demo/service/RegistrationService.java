package com.reslink.demo.service;

import com.reslink.demo.dto.RegistrationRequestDto;
import com.reslink.demo.model.User;
import com.reslink.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Injeta o codificador de senhas

    public User registerNewUser(RegistrationRequestDto registrationRequest) {
        // 1. Verifica se o email já está em uso
        if (userRepository.findByEmail(registrationRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Erro: Email já está em uso!");
        }

        // 2. Cria uma nova instância do usuário
        User newUser = new User();
        newUser.setEmail(registrationRequest.getEmail());

        // 3. Codifica a senha antes de salvar
        newUser.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));

        // 4. Define a localização inicial
        newUser.setLastLatitude(registrationRequest.getLatitude());
        newUser.setLastLongitude(registrationRequest.getLongitude());

        // 5. Salva o novo usuário no banco de dados
        return userRepository.save(newUser);
    }
}