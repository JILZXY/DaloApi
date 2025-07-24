package org.example.routes;

import io.javalin.Javalin;
import org.example.controller.ChatController;

public class ChatRoutes {
    private final ChatController chatController;

    public ChatRoutes(ChatController chatController) {
        this.chatController = chatController;
    }

    public void register(Javalin app){
        app.get("/chat", chatController::findAllChat);
        app.post("/chat", chatController::saveChat);
        app.put("/chat/{id}", chatController::updateChat);
        app.delete("/chat{id}", chatController::deleteChat);
        app.get("/chat/buscar", chatController::findByName);
        app.get("/chat/usuario/{id}", chatController::findChatsByUsuario);
    }
}
