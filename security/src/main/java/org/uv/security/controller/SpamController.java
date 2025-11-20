package org.uv.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.uv.security.service.ClasificadorService;
import org.uv.security.service.HClasificacionService;
import org.uv.security.service.HClasificacionService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/spam")
public class SpamController {

    @Autowired
    private ClasificadorService clasificadorService;

    @Autowired
    private HClasificacionService clasificacionService;

    @PostMapping("/clasificar")
    public ResponseEntity<Map<String, Object>> clasificar(@RequestBody Map<String, String> body) {
        String mensaje = body.get("texto");

        // Llamar al clasificador Python
        Map<String, Object> resultado = clasificadorService.clasificarMensaje(mensaje);
        System.out.println(resultado);
        // Extraer categoria y confianza directamente
        String categoria = (String) resultado.get("categoria");
        Double confianza = ((Number) resultado.get("confianza")).doubleValue();

        // Guardar en la base de datos
        clasificacionService.guardar(categoria, confianza);
        System.out.print(categoria);
        // Preparar respuesta para React
        Map<String, Object> response = new HashMap<>();
        response.put("categoria", categoria);
        response.put("confianza", confianza);

        return ResponseEntity.ok(response);
    }
}
