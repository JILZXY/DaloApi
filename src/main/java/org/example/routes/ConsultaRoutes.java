package org.example.routes;

import io.javalin.Javalin;
import org.example.controller.ConsultaController;

public class ConsultaRoutes {
    private final ConsultaController consultaController;

    public ConsultaRoutes(ConsultaController consultaController) {
        this.consultaController = consultaController;
    }

    public void register(Javalin app){
        app.get("/consultas", consultaController::findAllConsulta);
        app.post("/consultas", consultaController::saveConsulta);
        app.put("/consultas/{id}", consultaController::updateConsulta);
        app.delete("/consultas/{id}", consultaController::deleteConsulta);
        app.get("/consultas/materia", consultaController::findBymateria);
        app.get("/consultas/localidad", consultaController::findByMunEst);
        app.get("/consultas/materiaLocalidad", consultaController::findByMatMunEst);
        app.get("/consultas/usuario/{id}/total", consultaController::getTotalConsultasPorUsuario);
        app.get("/consultas/{id}", consultaController::obtenerConsultaPorId);
        app.get("/consultas/por-id/{id}", consultaController::findByUsuarioId);
    }
}
