package org.uv.security.controller;
import org.springframework.web.bind.annotation.*;
import org.uv.security.dto.ClasificacionRequest;
import org.uv.security.entity.Clasificacion;
import org.uv.security.service.HClasificacionService;

import java.util.List;

@RestController
@RequestMapping("/api/clasificaciones")
public class ClasificacionController {

    private final HClasificacionService service;

    public ClasificacionController(HClasificacionService service) {
        this.service = service;
    }

    // Guardar clasificación
    @PostMapping
    public Clasificacion guardar(@RequestBody ClasificacionRequest req) {
        return service.guardar(req.getCategoria(), req.getConfianza());
    }

    // Obtener todas
    @GetMapping
    public List<Clasificacion> obtenerTodas() {
        return service.obtenerTodas();
    }
}
