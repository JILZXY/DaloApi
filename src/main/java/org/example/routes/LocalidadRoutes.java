package org.example.routes;

import io.javalin.Javalin;
import org.example.controller.LocalidadController;

public class LocalidadRoutes {
    private final LocalidadController localidadController;

    public LocalidadRoutes(LocalidadController localidadController) {
        this.localidadController = localidadController;
    }

    public void register(Javalin app) {
        app.get("/localidad", localidadController::getAll);
        app.post("/localidad", localidadController::createLocalidad);
        app.put("/localidad/{id}", localidadController::updateLocalidad);
        app.delete("/localidad/{id}", localidadController::deleteLocalidad);
        app.get("/localidades/{id}", localidadController::obtenerLocalidadPorId);

    }
}
