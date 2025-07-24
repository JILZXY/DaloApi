package org.example.model;

public class Materia {
    private int idMateria;    // Corresponde a id_materia en la tabla MATERIA
    private String nombre;    // Corresponde a nombre en la tabla MATERIA
    private String descripcion; // Corresponde a descripcion en la tabla MATERIA

    public Materia(){}

    public Materia(int idMateria, String nombre, String descripcion) {
        this.idMateria = idMateria;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
