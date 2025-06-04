package com.reslink.demo.service;

import com.reslink.demo.dto.EventoDTO;
import org.json.JSONException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

@Service // Define que essa classe é um componente de serviço do Spring
public class EonetService {

    private final RestTemplate restTemplate;

    // @Autowired é opcional no construtor a partir do Spring 4.3+, mas pode deixar explícito
    public EonetService() {
        this.restTemplate = new RestTemplate();
    }

    public List<EventoDTO> buscarEventosAtivos() throws JSONException {
        String url = "https://eonet.gsfc.nasa.gov/api/v3/events?status=open&limit=50";
        JSONObject response = new JSONObject(restTemplate.getForObject(url, String.class));
        JSONArray eventos = response.getJSONArray("events");

        List<EventoDTO> lista = new ArrayList<>();
        for (int i = 0; i < eventos.length(); i++) {
            JSONObject evento = eventos.getJSONObject(i);

            JSONArray geometria = evento.getJSONArray("geometry");
            JSONObject ponto = geometria.getJSONObject(0);
            JSONArray coordenadas = ponto.getJSONArray("coordinates");

            EventoDTO dto = new EventoDTO();
            dto.setTitulo(evento.getString("title"));
            dto.setDescricao(evento.optString("description", "Sem descrição"));
            dto.setLongitude(coordenadas.getDouble(0));
            dto.setLatitude(coordenadas.getDouble(1));
            dto.setData(ponto.getString("date"));

            lista.add(dto);
        }
        return lista;
    }
}
