package org.example.routes;

import io.javalin.Javalin;
import org.example.controller.Reportecontroller;

public class ReporteRoutes {
    private final Reportecontroller reportecontroller;

    public ReporteRoutes(Reportecontroller reportecontroller) {
        this.reportecontroller = reportecontroller;
    }

    public void register(Javalin app){
        app.delete("/reporte/{id}", reportecontroller::deleteReporte);
        app.get("/reporte", reportecontroller::findAllReporte);
        app.post("/reporte", reportecontroller::saveReporte);
        app.put("/reporte/{id}", reportecontroller::updateReporte);
    }
}
