package org.example.routes;

import io.javalin.Javalin;
import org.example.controller.RespuestaConsultaController;

public class RespuestaConsultaRouste {
    private final RespuestaConsultaController respuestaConsultaController;

    public RespuestaConsultaRouste(RespuestaConsultaController respuestaConsultaController) {
        this.respuestaConsultaController = respuestaConsultaController;
    }

    public void register(Javalin app){
        app.get("/respuestaConsulta", respuestaConsultaController::findAllResCons);
        app.post("/respuestaConsulta", respuestaConsultaController::savaResCons);
        app.put("/respuestaConsulta/{id}", respuestaConsultaController::updateResCons);
        app.delete("/respuestaConsulta/{id}", respuestaConsultaController::deleteResCons);
        app.get("/respuestas/abogado/{id}/total", respuestaConsultaController::getTotalRespuestasPorAbogado);
        app.get("/respuestas/abogado/{id}", respuestaConsultaController::findByUsuarioAbogado);
        app.get("/respuestas/consulta/{id}", respuestaConsultaController::findByIdConsulta);
    }
}
