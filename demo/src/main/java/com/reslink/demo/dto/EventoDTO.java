package com.reslink.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data // Gera getters, setters, toString, equals, hashCode
@NoArgsConstructor // Gera construtor vazio
@AllArgsConstructor // Gera construtor com todos os campos
public class EventoDTO {
    private String titulo;
    private String descricao;
    private double latitude;
    private double longitude;
    private String data;
}
