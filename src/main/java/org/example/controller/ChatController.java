package org.example.controller;

import io.javalin.http.NotFoundResponse;
import org.example.model.Chat;
import org.example.service.ChatService;

import io.javalin.http.Context;
import java.sql.SQLException;
import java.util.List;

public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    public void findAllChat(Context ctx) {
        try {
            List<Chat> chats = chatService.findAllChat();
            ctx.json(chats);
        } catch (SQLException e) {
            ctx.status(500).result("Error al obtener los chats");
        }
    }

    public void saveChat(Context ctx) {
        Chat nuevo = ctx.bodyAsClass(Chat.class);
        int idGenerado = chatService.saveChat(nuevo);
        ctx.status(200).json(idGenerado);
    }

    public void updateChat(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        Chat chat = ctx.bodyAsClass(Chat.class);
        if (chatService.updateChat(id, chat)) {
            ctx.status(204);
        } else {
            throw new NotFoundResponse("No se actualizó el chat");
        }
    }

    public void deleteChat(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        if (chatService.deleteChat(id)) {
            ctx.status(204);
        } else {
            throw new NotFoundResponse("No se eliminó el chat");
        }
    }

    public void findByName(Context ctx) {
        try {
            String nombre = ctx.queryParam("nombre").toLowerCase();
            List<Chat> chats = chatService.findByName(nombre);
            if (chats.isEmpty()){
                ctx.status(404).result("No se encontraron chats con ese nombre.");
            } else {
                ctx.json(chats);
            }
        } catch (SQLException e) {
            ctx.status(500).result("Error al buscar chats por nombre");
        }
    }

    public void findChatsByUsuario(Context ctx) {
        int idUsuario = ctx.pathParamAsClass("id", Integer.class).get();

        try {
            List<Chat> chats = chatService.findByUsuario(idUsuario);
            if (chats.isEmpty()) {
                ctx.status(404).result("No se encontraron chats para el usuario con ID: " + idUsuario);
            } else {
                ctx.json(chats);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            ctx.status(500).result("Error al buscar chats por ID de usuario");
        }
    }
}
