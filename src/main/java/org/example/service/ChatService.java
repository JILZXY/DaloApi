package org.example.service;

import org.example.model.Chat;
import org.example.repository.ChatRepository;

import java.sql.SQLException;
import java.util.List;

public class ChatService {
    private final ChatRepository chatRepository;

    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public List<Chat> findAllChat() throws SQLException {
        return chatRepository.findAll();
    }

    public int saveChat(Chat chat){
        return chatRepository.save(chat);
    }

    public boolean updateChat(int id, Chat chat){
        return chatRepository.update(id, chat);
    }

    public boolean deleteChat(int id){
        return  chatRepository.delete(id);
    }

    public List<Chat> findByName(String nombre) throws SQLException {
        return chatRepository.findByNombreUsuario(nombre);
    }

    public List<Chat> findByUsuario(int idUsuario) throws SQLException {
        return chatRepository.findByUsuarioId(idUsuario);
    }
}
