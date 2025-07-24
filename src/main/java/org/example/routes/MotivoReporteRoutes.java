package org.example.routes;

import io.javalin.Javalin;
import org.example.controller.MotivoReporteController;

public class MotivoReporteRoutes {
    private final MotivoReporteController motivoReporteController;

    public MotivoReporteRoutes(MotivoReporteController motivoReporteController) {
        this.motivoReporteController = motivoReporteController;
    }

    public void register(Javalin app){
        app.get("/motivoReporte", motivoReporteController::findAllMotRepo);
        app.post("/motivoReporte", motivoReporteController::saveMotRepo);
        app.put("/motivoReporte/{id}", motivoReporteController::updateMotRepo);
        app.delete("/motivoReporte/{id}", motivoReporteController::deleteMotRepo);
    }
}
