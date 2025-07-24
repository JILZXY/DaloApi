package org.example.routes;

import io.javalin.Javalin;
import org.example.controller.UsuarioController;

public class UsuarioRoutes {
    private final UsuarioController usuarioController;

    public UsuarioRoutes(UsuarioController usuarioController) {
        this.usuarioController = usuarioController;
    }

    public void register(Javalin app){
        app.get("/usuarios", usuarioController::findAllUsuario);
        app.post("/usuarios", usuarioController::createUsuario);
        app.put("/usuarios/{id}", usuarioController::updateUsuario);
        app.delete("/usuarios/{id}", usuarioController::deleteUsuario);
        app.get("/usuarios/porId/{id}", usuarioController::findByIdUsuario);
        app.get("/usuarios/buscar/{nombre}", usuarioController::findByNameAbogados);
        app.get("/usuarios/abogados/materia", usuarioController::findAbogadosByMateria);
        app.get("/usuarios/abogados/localidad", usuarioController::findAbogadosByLocalidad);
        app.get("/usuarios/abogados/filtro", usuarioController::findAbogadosPorMateriaYLocalidad);
        app.get("/usuarios/email/{email}", usuarioController::findByEmail);
        app.get("/usuarios/materias/{id}", usuarioController::findMatByIdAbo);
        app.get("/usuarios/activos", usuarioController::findByActive);
        app.post("/usuarios/login", usuarioController::login);
        app.get("/usuarios/abogados", usuarioController::obtenerAbogados);
        app.get("/usuarios/abogados/{id}", usuarioController::obtenerAbogadoPorId);
        app.put("/usuarios/{id}/sin-contrasena", usuarioController::updateUsuarioSinContraseña);
    }
}
