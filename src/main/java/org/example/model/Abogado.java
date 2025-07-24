package org.example.model;

public class Abogado {

    private int idUsuario;
    private String cedulaProfesional;
    private String biografia;
    private String descripcion;


    public Abogado() {}


    public Abogado(int idUsuario, String cedulaProfesional, String biografia, String descripcion) {
        this.idUsuario = idUsuario;
        this.cedulaProfesional = cedulaProfesional;
        this.biografia = biografia;
        this.descripcion = descripcion;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCedulaProfesional() {
        return cedulaProfesional;
    }

    public void setCedulaProfesional(String cedulaProfesional) {
        this.cedulaProfesional = cedulaProfesional;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


}
