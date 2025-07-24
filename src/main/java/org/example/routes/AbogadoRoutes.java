package org.example.routes;

import io.javalin.Javalin;
import org.example.controller.AbogadoController;

public class AbogadoRoutes {
    private final AbogadoController abogadoController;

    public AbogadoRoutes(AbogadoController abogadoController) {
        this.abogadoController = abogadoController;
    }

    public void register(Javalin app){
        app.get("/abogado", abogadoController::findAllAbogados);
        app.post("/abogado", abogadoController::saveAbogado);
        app.put("/abogado/{id}", abogadoController::updateAbogado);
        app.delete("/abogado/{id}", abogadoController::deleteAbogado);
    }
}
