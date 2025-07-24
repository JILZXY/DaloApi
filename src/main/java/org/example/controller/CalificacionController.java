package org.example.controller;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import org.example.model.Calificacion;
import org.example.service.CalificacionService;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class CalificacionController {
    private final CalificacionService calificacionService;

    public CalificacionController(CalificacionService calificacionService) {
        this.calificacionService = calificacionService;
    }

    public void findAllCalificacion(Context ctx){
        try {
            List<Calificacion> calificaciones = calificacionService.finAllCalificacion();
            ctx.json(calificaciones);
        } catch (SQLException e) {
            ctx.status(500).result("Error al obtener las calificaciones");
        }
    }

    public void saveCalificaciones(Context ctx){
        Calificacion calificacion = ctx.bodyAsClass(Calificacion.class);
        int idGenerado = calificacionService.saveCalificacion(calificacion);
        ctx.status(200).json(idGenerado);
    }

    public void updateCalificaciones(Context ctx){
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        Calificacion calificacion = ctx.bodyAsClass(Calificacion.class);
        if (calificacionService.updateCalificacion(id, calificacion)){
            ctx.status(204);
        } else {
            throw new NotFoundResponse("No se encontro la calificacion a actualizar");
        }
    }

    public void deleteCalificacion(Context ctx){
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        if (calificacionService.deleteCalificacion(id)){
            ctx.status(240);
        } else {
            throw new NotFoundResponse("No se encontro la calificacion a eliminar");
        }
    }

    public void promedioAbogado(Context ctx) {
        int idAbogado = ctx.pathParamAsClass("id", Integer.class).get();

        try {
            Map<String, BigDecimal> resumen = calificacionService.promedioAbogado(idAbogado);
            ctx.json(resumen);
        } catch (SQLException e) {
            e.printStackTrace(); // Para ver el error en consola
            ctx.status(500).result("Error al obtener el resumen de calificaciones: " + e.getMessage());
        }
    }

    public void promediosPorRespuesta(Context ctx) {
        int idRespuesta = ctx.pathParamAsClass("id", Integer.class).get();

        try {
            Map<String, BigDecimal> resumen = calificacionService.promedioRespuesta(idRespuesta);
            ctx.json(resumen);
        } catch (SQLException e) {
            e.printStackTrace(); // Para debug en consola
            ctx.status(500).result("Error al obtener los promedios de la calificación para la respuesta: " + e.getMessage());
        }
    }
}
