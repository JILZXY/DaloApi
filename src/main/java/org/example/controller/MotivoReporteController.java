package org.example.controller;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import org.example.model.MotivoReporte;
import org.example.service.MotivoReporteService;

import java.sql.SQLException;
import java.util.List;

public class MotivoReporteController {
    private final MotivoReporteService motivoReporteService;

    public MotivoReporteController(MotivoReporteService motivoReporteService) {
        this.motivoReporteService = motivoReporteService;
    }

    public void findAllMotRepo(Context ctx){
        try {
            List<MotivoReporte> motivoReportes = motivoReporteService.findAllMotRepo();
            ctx.json(motivoReportes);
        } catch (SQLException e) {
            throw new NotFoundResponse("No se encontraron los motivos del reposte");
        }
    }

    public void saveMotRepo(Context ctx) throws SQLException {
        MotivoReporte dato = ctx.bodyAsClass(MotivoReporte.class);
        int idGenerado = motivoReporteService.saveMotRepo(dato);
        ctx.status(201).json(idGenerado);
    }

    public void updateMotRepo(Context ctx){
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        MotivoReporte motivoReporte = ctx.bodyAsClass(MotivoReporte.class);
        try {
            if (motivoReporteService.updateMotRepo(id, motivoReporte)){
                ctx.status(204);
            } else {
                throw new NotFoundResponse("Motivos de reporte no encontrado");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteMotRepo(Context ctx){
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        if (motivoReporteService.deleteMotRepo(id)){
            ctx.status(204);
        } else {
            throw  new NotFoundResponse("Motivo de reporte no eliminado");
        }
    }
}
