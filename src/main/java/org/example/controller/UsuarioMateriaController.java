package org.example.controller;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import org.example.model.UsuarioMateria;
import org.example.service.UsuarioMateriaService;

import java.sql.SQLException;
import java.util.List;
public class UsuarioMateriaController {
    private final UsuarioMateriaService usuarioMateriaService;

    public UsuarioMateriaController(UsuarioMateriaService usuarioMateriaService) {
        this.usuarioMateriaService = usuarioMateriaService;
    }

    public void findAllUsuarioMateria(Context ctx) {
        try {
            List<UsuarioMateria> relaciones = usuarioMateriaService.findAllUserMateria();
            ctx.json(relaciones);
        } catch (SQLException e) {
            ctx.status(500).result("Error al obtener las relaciones usuario-materia");
        }
    }

    public void saveUsuarioMateria(Context ctx) {
        UsuarioMateria nueva = ctx.bodyAsClass(UsuarioMateria.class);
        int idGenerado = usuarioMateriaService.saveUserMateria(nueva);
        ctx.status(200).json(idGenerado);
    }

    public void updateUsuarioMateria(Context ctx) {
        int idUsuarioActual = ctx.pathParamAsClass("idUsuario", Integer.class).get();
        int idMateriaActual = ctx.pathParamAsClass("idMateria", Integer.class).get();
        UsuarioMateria nuevaRelacion = ctx.bodyAsClass(UsuarioMateria.class);
        if (usuarioMateriaService.updateUserMateria(idUsuarioActual, idMateriaActual, nuevaRelacion)) {
            ctx.status(204);
        } else {
            throw new NotFoundResponse("No se actualizó la relación usuario-materia");
        }
    }

    public void deleteUsuarioMateria(Context ctx) {
        int idUsuario = ctx.pathParamAsClass("idUsuario", Integer.class).get();
        int idMateria = ctx.pathParamAsClass("idMateria", Integer.class).get();
        if (usuarioMateriaService.deleteUserMateria(idUsuario, idMateria)) {
            ctx.status(204);
        } else {
            throw new NotFoundResponse("No se eliminó la relación usuario-materia");
        }
    }

}
