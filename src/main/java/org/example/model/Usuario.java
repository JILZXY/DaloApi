package org.example.model;

import java.sql.Timestamp;
import java.math.BigDecimal;

import java.sql.Timestamp;

public class Usuario {

    private int idUsuario;
    private String nombre;
    private String email;
    private int idRol;
    private int idLocalidad;
    private boolean activo;
    private String contraseña;

    // Solo se usa si el usuario es abogado (idRol == 2)
    private Abogado abogado;

    public Usuario() {}

    public Usuario(int idUsuario, String nombre, String email, int idRol, int idLocalidad,
                   boolean activo, String contraseña) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.email = email;
        this.idRol = idRol;
        this.idLocalidad = idLocalidad;
        this.activo = activo;
        this.contraseña = contraseña;
    }

    //Constructor por si el usuario es abogado, sirve para la busqueda del usuario
    public Usuario(int idUsuario, String nombre, String email, int idRol, int idLocalidad,
                   boolean activo, Abogado abogado, String contraseña) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.email = email;
        this.idRol = idRol;
        this.idLocalidad = idLocalidad;
        this.activo = activo;
        this.contraseña = contraseña;
        this.abogado = abogado;
    }

    // Getters y Setters
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public int getIdLocalidad() {
        return idLocalidad;
    }

    public void setIdLocalidad(int idLocalidad) {
        this.idLocalidad = idLocalidad;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Abogado getAbogado() {
        return abogado;
    }

    public void setAbogado(Abogado abogado) {
        this.abogado = abogado;
    }

    // Utilidad: verifica si el usuario tiene rol de abogado
    public boolean esAbogado() {
        return idRol == 2;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}
