package org.example.model;

/*siempre se crea una clase para los atributos de tu tabla
* en esta clase todos los tipos de datos deben de coincidir con
* los de tu base de datos*/

public class Rol {
    private String nombre;
    private int id_rol;

    public Rol(){}

    public Rol(String nombre, int id_rol) {
        this.nombre = nombre;
        this.id_rol = id_rol;
    }

    public int getId_rol() {
        return id_rol;
    }

    public void setId_rol(int id_rol) {
        this.id_rol = id_rol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}