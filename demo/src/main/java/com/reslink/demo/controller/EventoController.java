package com.reslink.demo.controller;

import org.springframework.web.bind.annotation.*;


import com.reslink.demo.dto.EventoDTO;
import com.reslink.demo.service.EonetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {

    @Autowired
    private EonetService eonetService;

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<EventoDTO>> listarEventos() {
        return ResponseEntity.ok(eonetService.buscarEventosAtivos());
    }
}
