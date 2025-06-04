package com.reslink.demo.controller;

import com.reslink.demo.dto.EventoDTO;
import com.reslink.demo.service.EonetService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {

    @Autowired
    private EonetService eonetService;

    @GetMapping("/ativos")
    public ResponseEntity<List<EventoDTO>> listarEventos() throws JSONException {
        return ResponseEntity.ok(eonetService.buscarEventosAtivos());
    }
}
