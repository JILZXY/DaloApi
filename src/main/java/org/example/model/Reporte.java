package org.example.model;

import java.sql.Timestamp;

public class Reporte {
    private int idReporte;
    private int idUsuarioReporta;
    private int idUsuarioReportado;
    private int idMotivoReporte;
    private int idConsulta;
    private Timestamp fechaReporte;
    private String comentarios;

    public Reporte(){}

    public Reporte(int idReporte, int idUsuarioReporta, int idUsuarioReportado, int idMotivoReporte, Timestamp fechaReporte, String comentarios, int idConsulta) {
        this.idReporte = idReporte;
        this.idUsuarioReporta = idUsuarioReporta;
        this.idUsuarioReportado = idUsuarioReportado;
        this.idMotivoReporte = idMotivoReporte;
        this.fechaReporte = fechaReporte;
        this.comentarios = comentarios;
        this.idConsulta = idConsulta;
    }

    public int getIdReporte() {
        return idReporte;
    }

    public void setIdConsulta(int idConsulta){ this.idConsulta = idConsulta; }

    public int getIdConsulta(){ return idConsulta; }

    public void setIdReporte(int idReporte) {
        this.idReporte = idReporte;
    }

    public int getIdUsuarioReporta() {
        return idUsuarioReporta;
    }

    public void setIdUsuarioReporta(int idUsuarioReporta) {
        this.idUsuarioReporta = idUsuarioReporta;
    }

    public int getIdUsuarioReportado() {
        return idUsuarioReportado;
    }

    public void setIdUsuarioReportado(int idUsuarioReportado) {
        this.idUsuarioReportado = idUsuarioReportado;
    }

    public int getIdMotivoReporte() {
        return idMotivoReporte;
    }

    public void setIdMotivoReporte(int idMotivoReporte) {
        this.idMotivoReporte = idMotivoReporte;
    }

    // Setter adicional para fecha como long (para Jackson)
    public void setFechaReporte(long fechaMillis) {
        this.fechaReporte = new Timestamp(fechaMillis);
    }

    // Getters y setters estándar
    public Timestamp getFechaReporte() { return fechaReporte; }
    public void setFechaReporte(Timestamp fechaReporte) { this.fechaReporte = fechaReporte; }



    public String getComentarios() {
        return comentarios;
    }


    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
}
