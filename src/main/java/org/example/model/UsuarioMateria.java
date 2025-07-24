package org.example.model;

public class UsuarioMateria {
    private int idUsuario; // Corresponde a id_usuario en la tabla USUARIO_MATERIA
    private int idMateria; // Corresponde a id_materia en la tabla USUARIO_MATERIA

    public UsuarioMateria(){}

    public UsuarioMateria(int idUsuario, int idMateria) {
        this.idUsuario = idUsuario;
        this.idMateria = idMateria;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }
}
