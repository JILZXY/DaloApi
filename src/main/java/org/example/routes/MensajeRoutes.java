package org.example.routes;

import io.javalin.Javalin;
import org.example.controller.MensajeController;

public class MensajeRoutes {
    private final MensajeController mensajeController;

    public MensajeRoutes(MensajeController mensajeController) {
        this.mensajeController = mensajeController;
    }

    public void register(Javalin app) {
        app.get("/mensaje", mensajeController::findAllMensaje);
        app.post("/mensaje",   mensajeController::createMensaje);
        app.put("/mensaje/{id}", mensajeController::updateMensaje);
        app.delete("/mensaje/{id}", mensajeController::deleteMensaje);
        app.get("/mensajes/chat/{id}", mensajeController::findMensajesPorChat);
    }
}
