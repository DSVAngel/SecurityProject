package org.uv.security.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "clasificaciones")
public class Clasificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String categoria;

    private Double confianza;

    private LocalDateTime fecha;

    public Clasificacion() {}

    public Clasificacion(String categoria, Double confianza) {
        this.categoria = categoria;
        this.confianza = confianza;
        this.fecha = LocalDateTime.now();
    }

    // Getters y setters
    public Long getId() { return id; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public Double getConfianza() { return confianza; }
    public void setConfianza(Double confianza) { this.confianza = confianza; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}
