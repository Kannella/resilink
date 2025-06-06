package com.reslink.demo.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Usa o BCrypt, o padrão ouro para hashing de senhas
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Desabilita CSRF e permite acesso público aos nossos endpoints de auth
        // Em uma aplicação real, você configuraria regras de autorização mais detalhadas.
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**", "/h2-console/**").permitAll() // Permite acesso a /api/auth e /h2-console
                        .anyRequest().authenticated() // Exige autenticação para qualquer outra rota
                );
        // Necessário para o console H2 funcionar corretamente com Spring Security
        http.headers(headers -> headers.frameOptions().disable());

        return http.build();
    }
}