package org.example.controller;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import org.example.model.Consulta;
import org.example.service.ConsultaService;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ConsultaController {
    private final ConsultaService consultaService;

    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    public void findAllConsulta(Context ctx){
        try {
            List<Consulta> consultas = consultaService.findAllConsulta();
            ctx.json(consultas);
        } catch (SQLException e) {
            ctx.status(500).result("Error al obtener las calificaciones");
        }
    }

    public void saveConsulta(Context ctx){
        Consulta nuevo = ctx.bodyAsClass(Consulta.class);
        int idGenerado = 0;
        try {
            idGenerado = consultaService.saveConsulta(nuevo);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ctx.status(200).json(idGenerado);
    }

    public void updateConsulta(Context ctx){
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        Consulta consulta = ctx.bodyAsClass(Consulta.class);
        if (consultaService.updateConsulta(id, consulta)){
            ctx.status(204);
        } else {
            throw new NotFoundResponse("No se actulizo la consulta");
        }
    }

    public void deleteConsulta(Context ctx){
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        if (consultaService.deleteConsulta(id)){
            ctx.status(204);
        } else {
            throw new NotFoundResponse("No se elimino la consulta");
        }
    }

    public void findBymateria(Context ctx){
        String nombreMateria = ctx.queryParam("nombre");
        if (nombreMateria == null || nombreMateria.isBlank()) {
            ctx.status(400).result("El parámetro 'materia' es obligatorio");
            return;
        }

        try {
            List<Consulta> consultas = consultaService.findByMAteria(nombreMateria);
            ctx.json(consultas);
        } catch (SQLException e) {
            ctx.status(500).result("Error al buscar consultas por materia");
        }
    }

    public void findByMunEst(Context ctx){
        String estado = ctx.queryParam("estado");
        String municipio = ctx.queryParam("municipio");

        if (estado == null || estado.isBlank() || municipio == null || municipio.isBlank()) {
            ctx.status(400).result("Los parámetros 'estado' y 'municipio' son obligatorios");
            return;
        }

        try {
            List<Consulta> consultas = consultaService.findByMunEst(estado, municipio);
            ctx.json(consultas);
        } catch (SQLException e) {
            ctx.status(500).result("Error al buscar consultas por estado y municipio");
        }

    }

    public void findByMatMunEst(Context ctx){
        String nombreMateria = ctx.queryParam("nombre");
        String estado = ctx.queryParam("estado");
        String municipio = ctx.queryParam("municipio");

        if (nombreMateria == null || nombreMateria.isBlank() ||
                estado == null || estado.isBlank() ||
                municipio == null || municipio.isBlank()) {
            ctx.status(400).result("Los parámetros 'nombre', 'estado' y 'municipio' son obligatorios");
            return;
        }

        try {
            List<Consulta> consultas = consultaService.findByMatMunEst(nombreMateria, estado, municipio);
            ctx.json(consultas);
        } catch (SQLException e) {
            ctx.status(500).result("Error al buscar consultas por materia, estado y municipio");
        }
    }

    public void getTotalConsultasPorUsuario(Context ctx) {
        int idUsuario = ctx.pathParamAsClass("id", Integer.class).get();

        try {
            int total = consultaService.contarConsultas(idUsuario);
            ctx.json(Map.of(
                    "id_usuario", idUsuario,
                    "total_consultas", total
            ));
        } catch (Exception e) {
            ctx.status(500).result("Error al contar las consultas del usuario");
        }
    }

    public void obtenerConsultaPorId(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();

        try {
            Consulta consulta = consultaService.obtenerPorId(id);
            if (consulta != null) {
                ctx.json(consulta);
            } else {
                ctx.status(404).result("Consulta no encontrada");
            }
        } catch (SQLException e) {
            ctx.status(500).result("Error al obtener la consulta");
        }
    }

    public void findByUsuarioId(Context ctx) {
        int idUsuario = ctx.pathParamAsClass("id", Integer.class).get();

        try {
            List<Consulta> consultas = consultaService.obtenerPorUsuario(idUsuario);
            ctx.json(consultas);
        } catch (SQLException e) {
            ctx.status(500).result("Error al obtener consultas del usuario con ID: " + idUsuario);
        }
    }

}
