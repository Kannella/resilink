package com.reslink.demo.dto.alerts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlertsApiResponse {
    // O nome do atributo deve ser igual à chave principal do JSON ("estados")
    private List<EstadoDto> estados;
}