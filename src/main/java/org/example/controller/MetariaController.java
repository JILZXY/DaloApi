package org.example.controller;

import org.example.service.MateriaService;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import org.example.model.Materia;


import java.sql.SQLException;
import java.util.List;

public class MetariaController {
    private final MateriaService materiaService;

    public MetariaController(MateriaService materiaService) {
        this.materiaService = materiaService;
    }


    public void findAllMateria(Context ctx) {
        try {
            List<Materia> materias = materiaService.findAllMAteria();
            ctx.json(materias);
        } catch (SQLException e) {
            ctx.status(500).result("Error al obtener las materias");
        }
    }

    public void saveMateria(Context ctx) {
        Materia nueva = ctx.bodyAsClass(Materia.class);
        int idGenerado = materiaService.saveMateria(nueva);
        ctx.status(200).json(idGenerado);
    }

    public void updateMateria(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        Materia materia = ctx.bodyAsClass(Materia.class);
        if (materiaService.updateMateria(id, materia)) {
            ctx.status(204);
        } else {
            throw new NotFoundResponse("No se actualizó la materia");
        }
    }

    public void deleteMateria(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        if (materiaService.deleteMateria(id)) {
            ctx.status(204);
        } else {
            throw new NotFoundResponse("No se eliminó la materia");
        }
    }
}
