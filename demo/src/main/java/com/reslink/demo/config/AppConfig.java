package com.reslink.demo.config;


import com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // ADICIONE ESTE BEAN
    @Bean
    public Hibernate6Module hibernate6Module() {
        return new Hibernate6Module();
    }
}