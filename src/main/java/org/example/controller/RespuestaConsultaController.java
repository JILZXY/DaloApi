package org.example.controller;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import org.example.model.RespuestaConsulta;
import org.example.service.RespuestaConsultaService;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class RespuestaConsultaController {
    private final RespuestaConsultaService respuestaConsultaService;

    public RespuestaConsultaController(RespuestaConsultaService respuestaConsultaService) {
        this.respuestaConsultaService = respuestaConsultaService;
    }

    public void findAllResCons(Context ctx){
        try {
            List<RespuestaConsulta> respuestaConsultas = respuestaConsultaService.findAllResCons();
            ctx.json(respuestaConsultas);
        } catch (SQLException e) {
            throw new NotFoundResponse("Respuesta de consulta no encontrada");
        }
    }

    public void savaResCons(Context ctx){
        RespuestaConsulta respuestaConsulta = ctx.bodyAsClass(RespuestaConsulta.class);
        int id = 0;
        try {
            id = respuestaConsultaService.saveResCons(respuestaConsulta);
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear la respuesta");
        }
        ctx.status(201).json(id);
    }

    public void updateResCons(Context ctx){
        RespuestaConsulta respuestaConsulta = ctx.bodyAsClass(RespuestaConsulta.class);
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        try {
            if (respuestaConsultaService.updateResCons(id, respuestaConsulta)){
                ctx.status(201).json(id);
            } else {
                throw new NotFoundResponse("Error al actualizar la respuesta");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public  void deleteResCons(Context ctx){
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        if (respuestaConsultaService.deleteResCons(id)){
            ctx.status(201).json(id);
        } else {
            throw new NotFoundResponse("Respuesta de consulta no encontrada");
        }
    }

    public void getTotalRespuestasPorAbogado(Context ctx) {
        int idAbogado = ctx.pathParamAsClass("id", Integer.class).get();

        try {
            int total = respuestaConsultaService.contarResCon(idAbogado);
            ctx.json(Map.of(
                    "id_abogado", idAbogado,
                    "total_respuestas", total
            ));
        } catch (Exception e) {
            ctx.status(500).result("Error al contar las respuestas del abogado");
        }
    }

    public void findByUsuarioAbogado(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();

        try {
            List<RespuestaConsulta> respuestas = respuestaConsultaService.findByUsuarioAbogado(id);
            ctx.json(respuestas);
        } catch (SQLException e) {
            ctx.status(500).result("Error al obtener respuestas del abogado con ID: " + id);
        }
    }


    public void findByIdConsulta(Context ctx) {
        int idConsulta = ctx.pathParamAsClass("id", Integer.class).get();

        try {
            List<RespuestaConsulta> respuestas = respuestaConsultaService.findByIdConsulta(idConsulta);
            ctx.json(respuestas);
        } catch (SQLException e) {
            ctx.status(500).result("Error al obtener respuestas de la consulta con ID: " + idConsulta);
        }
    }
}
