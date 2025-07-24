package org.example.controller;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import org.example.model.Rol;
import org.example.service.RolService;

import java.sql.SQLException;
import java.util.List;

/*Sirve para cargar los formatos JSON en obejtos, aqui se pueden hacer validaciones pero si una validacion
* la repites varias veces en diferentes modelos no podras usarla y repetiras condifo por cada uno de estos modelos
* pero aqui puedes dar una respuesta rapida*/

public class RolController {
    private final RolService rolService;

    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    public void getAll(Context ctx) {
        try {
            List<Rol> rol = rolService.getAll();
            ctx.json(rol);
        } catch (SQLException e) {
            ctx.status(500).result("Error al obtener Rol");
        }
    }

    public void createRol(Context ctx) {
        Rol nuevo = ctx.bodyAsClass(Rol.class);
        int idGenerado = rolService.saveRol(nuevo);
        ctx.status(201).json(idGenerado);
    }

    public void updateRol(Context ctx){
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        Rol dato = ctx.bodyAsClass(Rol.class);
        if (rolService.updateRol(id, dato)) {
            ctx.status(204);
        } else {
            throw new NotFoundResponse("Rol no encontrado");
        }
    }

    public void deleteRol(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        if (rolService.deleteRol(id)) {
            ctx.status(204);
        } else {
            throw new NotFoundResponse("Rol no encontrado");
        }
    }
}