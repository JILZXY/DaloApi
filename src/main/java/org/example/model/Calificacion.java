package org.example.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;

public class Calificacion {
    private int idCalificacion; // id_calificacion [cite: 11]
    private int idUsuarioCliente; // id_usuario_cliente [cite: 11]
    private int idUsuarioAbogado; // id_usuario_abogado [cite: 11]
    private int idRespuesta; // id_respuesta [cite: 11]
    private Double atencion; // atencion (puede ser null si es INT en SQL y no hay valor) [cite: 11]
    private Double profesionalismo; // profesionalismo (puede ser null) [cite: 11]
    private Double claridad; // claridad (puede ser null) [cite: 11]
    private Double empatia; // empatia (puede ser null) [cite: 11]
    private Double calificacionGeneral; // calificacion_general (DECIMAL(3,2) a Double) [cite: 11]
    private Timestamp fechaCalificacion; // fecha_calificacion (TIMESTAMP) [cite: 11]

    public Calificacion(){}

    public Calificacion(int idCalificacion, int idUsuarioCliente, int idUsuarioAbogado, int idRespuesta, Double atencion, Double profesionalismo, Double claridad, Double empatia, Double calificacionGeneral, Timestamp fechaCalificacion) {
        this.idCalificacion = idCalificacion;
        this.idUsuarioCliente = idUsuarioCliente;
        this.idUsuarioAbogado = idUsuarioAbogado;
        this.idRespuesta = idRespuesta;
        this.atencion = atencion;
        this.profesionalismo = profesionalismo;
        this.claridad = claridad;
        this.empatia = empatia;
        this.calificacionGeneral = calificacionGeneral;
        this.fechaCalificacion = fechaCalificacion;
    }

    public int getIdCalificacion() {
        return idCalificacion;
    }

    public void setIdCalificacion(int idCalificacion) {
        this.idCalificacion = idCalificacion;
    }

    public int getIdUsuarioCliente() {
        return idUsuarioCliente;
    }

    public void setIdUsuarioCliente(int idUsuarioCliente) {
        this.idUsuarioCliente = idUsuarioCliente;
    }

    public int getIdUsuarioAbogado() {
        return idUsuarioAbogado;
    }

    public void setIdUsuarioAbogado(int idUsuarioAbogado) {
        this.idUsuarioAbogado = idUsuarioAbogado;
    }

    public int getIdRespuesta() {
        return idRespuesta;
    }

    public void setIdRespuesta(int idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    public Double getAtencion() {
        return atencion;
    }

    public void setAtencion(Double atencion) {
        this.atencion = atencion;
    }

    public Double getProfesionalismo() {
        return profesionalismo;
    }

    public void setProfesionalismo(Double profesionalismo) {
        this.profesionalismo = profesionalismo;
    }

    public Double getClaridad() {
        return claridad;
    }

    public void setClaridad(Double claridad) {
        this.claridad = claridad;
    }

    public Double getEmpatia() {
        return empatia;
    }

    public void setEmpatia(Double empatia) {
        this.empatia = empatia;
    }

    public Double getCalificacionGeneral() {
        return calificacionGeneral;
    }

    public void setCalificacionGeneral(Double calificacionGeneral) {
        this.calificacionGeneral = calificacionGeneral;
    }

    public Timestamp getFechaCalificacion() {
        return fechaCalificacion;
    }

    public void setFechaCalificacion(Timestamp fechaCalificacion) {
        this.fechaCalificacion = fechaCalificacion;
    }

    public double calcularPromedio() {
        double promedio = (double)(atencion + profesionalismo + claridad + empatia) / 4.0;
        promedio = Math.round(promedio * 100.0) / 100.0;
        return promedio;
    }
}
