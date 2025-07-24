package org.example.routes;

import io.javalin.Javalin;
import org.example.controller.UsuarioMateriaController;

public class UsuarioMateriaRoutes {
    private final UsuarioMateriaController usuarioMateriaController;

    public UsuarioMateriaRoutes(UsuarioMateriaController usuarioMateriaController) {
        this.usuarioMateriaController = usuarioMateriaController;
    }

    public void register(Javalin app){
        app.get("/usuarioMateria", usuarioMateriaController::findAllUsuarioMateria);
        app.put("/usuarioMateria/{idUsuario}/{idMateria}", usuarioMateriaController::updateUsuarioMateria);
        app.post("/usuarioMateria", usuarioMateriaController::saveUsuarioMateria);
        app.delete("/usuarioMateria/{idUsuario}/{idMateria}", usuarioMateriaController::deleteUsuarioMateria);
    }
}
