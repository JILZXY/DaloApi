package org.example.model;

public class Localidad {
    private int idLocalidad;
    private String estado;
    private String municipio;

    public Localidad(){}

    public Localidad(int idLocalidad, String estado, String municipio) {
        this.idLocalidad = idLocalidad;
        this.estado = estado;
        this.municipio = municipio;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdLocalidad() {
        return idLocalidad;
    }

    public void setIdLocalidad(int idLocalidad) {
        this.idLocalidad = idLocalidad;
    }
}
