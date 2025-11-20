package org.uv.security.service;
import org.springframework.stereotype.Service;
import org.uv.security.entity.Clasificacion;
import org.uv.security.repository.ClasificacionRepository;

import java.util.List;

@Service
public class HClasificacionService {

    private final ClasificacionRepository repository;

    public HClasificacionService(ClasificacionRepository repository) {
        this.repository = repository;
    }

    public Clasificacion guardar(String categoria, Double confianza) {
        Clasificacion c = new Clasificacion(categoria, confianza);
        return repository.save(c);
    }

    public List<Clasificacion> obtenerTodas() {
        return repository.findAll();
    }
}
