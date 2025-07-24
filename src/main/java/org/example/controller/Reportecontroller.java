package org.example.controller;

import io.javalin.http.NotFoundResponse;
import org.example.model.Reporte;
import org.example.service.ReporteService;

import java.sql.SQLException;
import io.javalin.http.Context;
import java.util.List;

public class Reportecontroller {

    private final ReporteService reporteService;


    public Reportecontroller(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    public void findAllReporte(Context ctx) {
        try {
            List<Reporte> reportes = reporteService.findAllReporte();
            ctx.json(reportes);
        } catch (SQLException e) {
            ctx.status(500).result("Error al obtener los reportes");
        }
    }

    public void saveReporte(Context ctx) {
        try {
            Reporte nuevo = ctx.bodyAsClass(Reporte.class);

            // Validación mínima antes de guardar
            if (nuevo.getIdUsuarioReporta() <= 0 ||
                    nuevo.getIdUsuarioReportado() <= 0 ||
                    nuevo.getIdMotivoReporte() <= 0 ||
                    nuevo.getIdConsulta() <= 0 ||
                    nuevo.getFechaReporte() == null) {
                ctx.status(400).result("Faltan datos obligatorios o hay campos inválidos.");
                return;
            }



            int idGenerado = reporteService.saveReporter(nuevo);
            ctx.status(201).json(idGenerado); // 201: creado con éxito

        } catch (Exception e) {
            System.err.println("Error al guardar reporte: " + e.getMessage());
            ctx.status(500).result("Error interno al guardar el reporte.");
        }
    }

    public void updateReporte(Context ctx) {
        try {
            int id = ctx.pathParamAsClass("id", Integer.class).get();
            Reporte reporte = ctx.bodyAsClass(Reporte.class);

            // Validaciones básicas del objeto
            if (reporte.getIdUsuarioReporta() <= 0 ||
                    reporte.getIdUsuarioReportado() <= 0 ||
                    reporte.getIdMotivoReporte() <= 0 ||
                    reporte.getIdConsulta() <= 0 ||
                    reporte.getFechaReporte() == null) {
                ctx.status(400).result("Faltan datos obligatorios o hay campos inválidos.");
                return;
            }

            boolean actualizado = reporteService.updateReporte(id, reporte);
            if (actualizado) {
                ctx.status(204); // Sin contenido, exitoso
            } else {
                ctx.status(404).result("No se encontró el reporte con el ID proporcionado.");
            }

        } catch (Exception e) {
            System.err.println("Error al actualizar reporte con ID " + ctx.pathParam("id") + ": " + e.getMessage());
            ctx.status(500).result("Error interno al actualizar el reporte.");
        }
    }

    public void deleteReporte(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        try {
            if (reporteService.deleteReporte(id)) {
                ctx.status(204);
            } else {
                throw new NotFoundResponse("No se eliminó el reporte");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

