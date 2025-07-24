package org.example.routes;

import io.javalin.Javalin;
import org.example.controller.RolController;

/*Aqui se define la ruta en donde se encontraran los roles y las llaves "{}" sirven para indicar que parametro debe de recibir
* si llegara a buscar por un atributo en especifico*/

public class RolRutes {
    private final RolController rolController;

    public RolRutes(RolController rolController) {
        this.rolController = rolController;
    }

    public void register(Javalin app) {
        app.get("/rol", rolController::getAll);
        app.post("/rol",   rolController::createRol);
        app.put("/rol/{id}", rolController::updateRol);
        app.delete("/rol/{id}", rolController::deleteRol);
    }

}
