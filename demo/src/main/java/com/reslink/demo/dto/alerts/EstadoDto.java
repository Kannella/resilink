package com.reslink.demo.dto.alerts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EstadoDto {

    private String uf;
    private String nome;
    private List<AlertDto> alertas;
}