package org.example.routes;

import io.javalin.Javalin;
import org.example.controller.CalificacionController;

public class CalificacionesRoutes {
    private final CalificacionController calificacionController;

    public CalificacionesRoutes(CalificacionController calificacionController) {
        this.calificacionController = calificacionController;
    }

    public void register(Javalin app){
        app.get("/calificaciones", calificacionController::findAllCalificacion);
        app.post("/calificaciones", calificacionController::saveCalificaciones);
        app.put("/calificaciones/{id}", calificacionController::updateCalificaciones);
        app.delete("/calificaciones/{id}", calificacionController::deleteCalificacion);
        app.get("/calificaciones/promedio/{id}", calificacionController::promedioAbogado);
        app.get("/calificaciones/promedios/respuesta/{id}", calificacionController::promediosPorRespuesta);
    }
}
