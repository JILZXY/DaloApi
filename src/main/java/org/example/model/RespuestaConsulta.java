package org.example.model;

import java.sql.Timestamp;

public class RespuestaConsulta {
    private int idRespuesta; // id_respuesta
    private int idUsuarioAbogado; // id_usuario_abogado
    private int idConsulta; // id_consulta
    private String respuesta; // respuesta
    private Timestamp fechaRespuesta; // fecha_respuesta (TIMESTAMP)

    public RespuestaConsulta(){}

    public RespuestaConsulta(int idRespuesta, int idUsuarioAbogado, int idConsulta, String respuesta, Timestamp fechaRespuesta) {
        this.idRespuesta = idRespuesta;
        this.idUsuarioAbogado = idUsuarioAbogado;
        this.idConsulta = idConsulta;
        this.respuesta = respuesta;
        this.fechaRespuesta = fechaRespuesta;
    }

    public int getIdRespuesta() {
        return idRespuesta;
    }

    public void setIdRespuesta(int idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    public int getIdUsuarioAbogado() {
        return idUsuarioAbogado;
    }

    public void setIdUsuarioAbogado(int idUsuarioAbogado) {
        this.idUsuarioAbogado = idUsuarioAbogado;
    }

    public int getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(int idConsulta) {
        this.idConsulta = idConsulta;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public Timestamp getFechaRespuesta() {
        return fechaRespuesta;
    }

    public void setFechaRespuesta(Timestamp fechaRespuesta) {
        this.fechaRespuesta = fechaRespuesta;
    }
}