package org.uv.security.dto;

public class ClasificacionRequest {

    private String categoria;
    private Double confianza;

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public Double getConfianza() { return confianza; }
    public void setConfianza(Double confianza) { this.confianza = confianza; }
}

