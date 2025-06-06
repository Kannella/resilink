package com.reslink.demo.dto.alerts;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) // Ignora campos do JSON que não mapeamos
public class AlertDto {

    private Long id;
    private String area;
    private String categoria;
    private String severidade;
    private String status;

    @JsonProperty("descricaoSms") // Mapeia o nome do campo no JSON para o atributo
    private String descricaoSms;

    @JsonProperty("orgaoEmissor")
    private String orgaoEmissor;

    @JsonProperty("dataCadastroEvento")
    private String dataCadastroEvento;

    @JsonProperty("dataEnvioAlerta")
    private String dataEnvioAlerta;
}