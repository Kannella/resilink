package com.reslink.demo.service;

import com.reslink.demo.dto.RegistrationRequestDto;
import com.reslink.demo.model.User;
import com.reslink.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service; // << IMPORTANTE

@Service // << ADICIONE ESTA ANOTAÇÃO
public class RegistrationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public String registerNewUserAndGetToken(RegistrationRequestDto registrationRequest) {
        if (userRepository.findByEmail(registrationRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Erro: Email já está em uso!");
        }

        User newUser = new User();
        newUser.setEmail(registrationRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        newUser.setLastLatitude(registrationRequest.getLatitude());
        newUser.setLastLongitude(registrationRequest.getLongitude());

        var savedUser = userRepository.save(newUser);
        return jwtService.generateToken(savedUser);
    }
}