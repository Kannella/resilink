package com.reslink.demo.service;

import com.reslink.demo.dto.alerts.AlertsApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;
import java.util.Collections;

@Service
public class AlertService {

    private static final String ALERTS_API_URL = "https://ixh2ffo24c.execute-api.us-east-1.amazonaws.com/prod/alertas";

    @Autowired
    private RestTemplate restTemplate;

    public AlertsApiResponse fetchAllAlerts() {
        try {
            // 1. Cria os cabeçalhos (Headers) da requisição
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            // 2. Simula um User-Agent de navegador, um truque comum para evitar bloqueios 403
            headers.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36");

            // 3. Cria a entidade da requisição com os cabeçalhos
            HttpEntity<String> entity = new HttpEntity<>("body", headers);

            // 4. Usa o método exchange() que é mais poderoso e permite enviar cabeçalhos
            ResponseEntity<AlertsApiResponse> response = restTemplate.exchange(ALERTS_API_URL, HttpMethod.GET, entity, AlertsApiResponse.class);

            return response.getBody();

        } catch (RestClientException e) {
            System.err.println("Erro ao chamar a API de alertas: " + e.getMessage());
            throw new RuntimeException("Não foi possível buscar os alertas da API externa.", e);
        }
    }
}