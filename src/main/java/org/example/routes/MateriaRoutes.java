package org.example.routes;

import io.javalin.Javalin;
import org.example.controller.MetariaController;

public class MateriaRoutes {
    private final MetariaController metariaController;

    public MateriaRoutes(MetariaController metariaController) {
        this.metariaController = metariaController;
    }

    public void register(Javalin app){
        app.get("/materia", metariaController::findAllMateria);
        app.post("/materia", metariaController::saveMateria);
        app.put("/materia/{id}", metariaController::updateMateria);
        app.delete("/materia/{id}", metariaController::deleteMateria);
    }
}
