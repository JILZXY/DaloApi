package org.example.model;

public class MotivoReporte {
    private int idMotivoReporte; // id_motivo_reporte
    private String nombre; // nombre
    private String descripcion; // descripcion
    private String reportePersonalizado; // reporte_personalizado

    public MotivoReporte(){}

    public MotivoReporte(int idMotivoReporte, String nombre, String descripcion, String reportePersonalizado) {
        this.idMotivoReporte = idMotivoReporte;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.reportePersonalizado = reportePersonalizado;
    }

    public int getIdMotivoReporte() {
        return idMotivoReporte;
    }

    public void setIdMotivoReporte(int idMotivoReporte) {
        this.idMotivoReporte = idMotivoReporte;
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

    public String getReportePersonalizado() {
        return reportePersonalizado;
    }

    public void setReportePersonalizado(String reportePersonalizado) {
        this.reportePersonalizado = reportePersonalizado;
    }
}