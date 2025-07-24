package org.example.controller;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import org.example.model.Localidad;
import org.example.model.Rol;
import org.example.service.LocalidadService;

import java.sql.SQLException;
import java.util.List;

public class LocalidadController {
    private final LocalidadService localidadService;

    public LocalidadController(LocalidadService localidadService) {
        this.localidadService = localidadService;
    }

    public void getAll(Context ctx) {
        try {
            List<Localidad> localidad = localidadService.findAllLocalidad();
            ctx.json(localidad);
        } catch (SQLException e) {
            ctx.status(500).result("Error al obtener localidad");
        }
    }

    public void createLocalidad(Context ctx) {
        Localidad nuevo = ctx.bodyAsClass(Localidad.class);
        int idGenerado = localidadService.saveLocalidad(nuevo);
        ctx.status(201).json(idGenerado);
    }

    public void updateLocalidad(Context ctx){
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        Localidad dato = ctx.bodyAsClass(Localidad.class);
        if (localidadService.updateLocalidad(id, dato)) {
            ctx.status(204);
        } else {
            throw new NotFoundResponse("Localidad no encontrado");
        }
    }

    public void deleteLocalidad(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        if (localidadService.deleteLocalidad(id)) {
            ctx.status(204);
        } else {
            throw new NotFoundResponse("Localidad no encontrado");
        }
    }

    public void obtenerLocalidadPorId(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();

        try {
            Localidad localidad = localidadService.obtenerPorId(id);
            if (localidad != null) {
                ctx.json(localidad);
            } else {
                ctx.status(404).result("Localidad no encontrada");
            }
        } catch (SQLException e) {
            ctx.status(500).result("Error al obtener la localidad");
        }
    }

}
