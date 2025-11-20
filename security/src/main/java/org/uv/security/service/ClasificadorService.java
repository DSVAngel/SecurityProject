package org.uv.security.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ClasificadorService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String URL_PYTHON = "http://localhost:8000/clasificar";

    public Map<String, Object> clasificarMensaje(String mensaje) {
        Map<String, String> payload = new HashMap<>();
        payload.put("texto", mensaje);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(payload);

        ResponseEntity<Map> response =
                restTemplate.postForEntity(URL_PYTHON, request, Map.class);

        Map<String, Object> respuestaPython = response.getBody();


        String etiqueta = (String) respuestaPython.get("etiqueta");

        Number confianzaNumber = (Number) respuestaPython.get("confianza");
        double confianza = confianzaNumber.doubleValue();


        Map<String, Object> resultado = new HashMap<>();
        resultado.put("categoria", etiqueta);
        resultado.put("confianza", confianza*100);

        return resultado;
    }
}

