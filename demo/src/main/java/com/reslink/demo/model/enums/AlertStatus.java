package com.reslink.demo.model.enums;

public enum AlertStatus {
    PENDING,  // Alerta recém-criado, aguardando votos
    CONFIRMED, // Atingiu o número necessário de confirmações
    REJECTED  // Atingiu o número necessário de negações
}