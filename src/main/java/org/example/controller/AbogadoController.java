package org.example.controller;

import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;


import java.sql.SQLException;
import java.util.List;

import io.javalin.http.NotFoundResponse;
import org.example.model.Abogado;
import org.example.service.AbogadoService;

public class AbogadoController {
    private final AbogadoService abogadoService;

    public AbogadoController(AbogadoService abogadoService) {
        this.abogadoService = abogadoService;
    }

    public void findAllAbogados(Context ctx){
        try {
            List<Abogado> abogados = abogadoService.findAllAbogado();
            ctx.json(abogados);
        } catch (SQLException e) {
            ctx.status(500).result("No se encontaron abogados");
        }
    }

    public void saveAbogado(Context ctx){
        try {
            Abogado abogado = ctx.bodyAsClass(Abogado.class);
            String cedulaProfesional = abogado.getCedulaProfesional();

            if (cedulaProfesional == null || cedulaProfesional.isEmpty()) {
                // Si la cédula es nula o vacía, lanzamos un error 400 (Bad Request)
                throw new BadRequestResponse("La cédula profesional no puede estar vacía.");
            }

            int idGenerado = abogadoService.saveAbogado(abogado);
            ctx.status(200).json(idGenerado);

        } catch (BadRequestResponse e) {
            // BadRequestResponse y enviamos el mensaje al cliente con status 400
            ctx.status(400).result(e.getMessage());
        } catch (SQLException e){
            ctx.status(500).result("No se pudo crear el abogado debido a un error de servidor: " + e.getMessage());
        } catch (Exception e) {
            ctx.status(500).result("Ocurrió un error inesperado al crear el abogado: " + e.getMessage());
        }
    }


    public void updateAbogado(Context ctx){
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        Abogado abogado = ctx.bodyAsClass(Abogado.class);
        try {
            String cedulaProfesional = abogado.getCedulaProfesional();

            if (cedulaProfesional == null || cedulaProfesional.isEmpty()) {
                throw new BadRequestResponse("La cédula profesional no puede estar vacía.");
            }

            if (abogadoService.updateAbogado(id, abogado)){
                ctx.status(204);
            } else {
                ctx.status(500).result("Error al actualizar el apartado del abogado");
            }
        } catch (BadRequestResponse e) {
            ctx.status(400).result(e.getMessage());
        } catch (SQLException e) {
            ctx.status(500).result("Error interno al actualizar el abogado: " + e.getMessage());
        }
    }

    public void deleteAbogado(Context ctx){
        int id = ctx.pathParamAsClass("id_usuario", Integer.class).get();
        try {
            if (abogadoService.deleteAboagdo(id)) {
                ctx.status(204);
            } else {
                throw new NotFoundResponse("No se eliminó el abogado");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
