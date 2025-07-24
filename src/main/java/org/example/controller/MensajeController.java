package org.example.controller;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import org.example.model.Mensaje;
import org.example.service.MensajeService;

import java.sql.SQLException;
import java.util.List;

public class MensajeController {
    private final MensajeService mensajeService;

    public MensajeController(MensajeService mensajeService) {
        this.mensajeService = mensajeService;
    }

    public void findAllMensaje(Context ctx) {
        try {
            List<Mensaje> mensajes = mensajeService.findAllMensajes();
            ctx.json(mensajes);
        } catch (SQLException e) {
            ctx.status(500).result("Error al obtener Mensaje");
        }
    }

    public void createMensaje(Context ctx) throws SQLException {
        Mensaje nuevo = ctx.bodyAsClass(Mensaje.class);
        int idGenerado = 0;
        try {
            idGenerado = mensajeService.saveMensaje(nuevo);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ctx.status(201).json(idGenerado);
    }

    public void updateMensaje(Context ctx){
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        Mensaje dato = ctx.bodyAsClass(Mensaje.class);
        try {
            if (mensajeService.updateMensaje(id, dato)) {
                ctx.status(204);
            } else {
                throw new NotFoundResponse("Mensaje no encontrado");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteMensaje(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        if (mensajeService.deleteMensaje(id)) {
            ctx.status(204);
        } else {
            throw new NotFoundResponse("Mensaje no encontrado");
        }
    }

    public void findMensajesPorChat(Context ctx) {
        int idChat = ctx.pathParamAsClass("id", Integer.class).get();

        try {
            List<Mensaje> mensajes = mensajeService.findMensajesPorChat(idChat);
            if (mensajes.isEmpty()) {
                ctx.status(404).result("No se encontraron mensajes para el chat con ID " + idChat);
            } else {
                ctx.json(mensajes);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            ctx.status(500).result("Error al obtener mensajes por chat");
        }
    }
}
