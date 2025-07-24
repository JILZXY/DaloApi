package org.example.model;

import java.sql.Timestamp;

public class Consulta {
    private int idConsulta; // id_consulta
    private int idUsuario; // id_usuario
    private String titulo; // titulo
    private String pregunta; // pregunta
    private Timestamp fechaPublicacion; // fecha_publicacion (TIMESTAMP)
    private int idLocalidad; // id_localidad
    private int idMateria; // id_materia

    public Consulta(){}

    public Consulta(int idConsulta, int idUsuario, String titulo, String pregunta, Timestamp fechaPublicacion, int idLocalidad, int idMateria) {
        this.idConsulta = idConsulta;
        this.idUsuario = idUsuario;
        this.titulo = titulo;
        this.pregunta = pregunta;
        this.fechaPublicacion = fechaPublicacion;
        this.idLocalidad = idLocalidad;
        this.idMateria = idMateria;
    }

    public int getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(int idConsulta) {
        this.idConsulta = idConsulta;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public Timestamp getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Timestamp fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public int getIdLocalidad() {
        return idLocalidad;
    }

    public void setIdLocalidad(int idLocalidad) {
        this.idLocalidad = idLocalidad;
    }

    public int getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }
}