package com.reslink.demo.controller;

import com.reslink.demo.dto.alerts.AlertsApiResponse;
import com.reslink.demo.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AlertController {

    @Autowired
    private AlertService alertService;

    @GetMapping("/alerts")
    public ResponseEntity<AlertsApiResponse> getAllAlerts() {
        // Chama o serviço para buscar os dados
        AlertsApiResponse alerts = alertService.fetchAllAlerts();
        // Retorna os dados com um status 200 OK
        return ResponseEntity.ok(alerts);
    }
}