package org.example.repository;

import org.example.config.DatabaseConfig;
import org.example.model.Chat;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChatRepository {

    // findAll: obtiene todos los chats
    public List<Chat> findAll() throws SQLException {
        List<Chat> chats = new ArrayList<>();
        String query = "SELECT * FROM CHAT";
        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                Chat chat = new Chat();
                chat.setIdChat(rs.getInt("id_chat"));
                chat.setIdCliente(rs.getInt("id_cliente"));
                chat.setIdAbogado(rs.getInt("id_abogado"));
                chat.setFechaInicio(rs.getTimestamp("fecha_inicio"));
                chats.add(chat);
            }
        }
        return chats;
    }

    // save: inserta un nuevo chat
    public int save(Chat chat) {
        String queryRol = "SELECT id_rol FROM USUARIO WHERE id_usuario = ?";
        String insertQuery = "INSERT INTO CHAT (id_cliente, id_abogado, fecha_inicio) VALUES (?, ?, ?)";

        try (Connection c = DatabaseConfig.getDataSource().getConnection()) {

            // Validar que id_cliente sea rol 1 (cliente)
            try (PreparedStatement psCliente = c.prepareStatement(queryRol)) {
                psCliente.setInt(1, chat.getIdCliente());
                ResultSet rsCliente = psCliente.executeQuery();
                if (!rsCliente.next() || rsCliente.getInt("id_rol") != 1) {
                    throw new RuntimeException("El idCliente no corresponde a un usuario con rol CLIENTE (rol 1)");
                }
            }

            // Validar que id_abogado sea rol 2 (abogado)
            try (PreparedStatement psAbogado = c.prepareStatement(queryRol)) {
                psAbogado.setInt(1, chat.getIdAbogado());
                ResultSet rsAbogado = psAbogado.executeQuery();
                if (!rsAbogado.next() || rsAbogado.getInt("id_rol") != 2) {
                    throw new RuntimeException("El idAbogado no corresponde a un usuario con rol ABOGADO (rol 2)");
                }
            }

            // Insertar si ambos roles son válidos
            try (PreparedStatement psInsert = c.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
                psInsert.setInt(1, chat.getIdCliente());
                psInsert.setInt(2, chat.getIdAbogado());
                psInsert.setTimestamp(3, chat.getFechaInicio());

                psInsert.executeUpdate();
                try (ResultSet rs = psInsert.getGeneratedKeys()) {
                    if (rs.next()) return rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar el chat", e);
        }

        return -1;
    }

    // update: actualiza un chat existente
    public boolean update(int id, Chat chat) {
        String query = "UPDATE CHAT SET id_cliente = ?, id_abogado = ?, fecha_inicio = ? WHERE id_chat = ?";
        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query)
        ) {
            ps.setInt(1, chat.getIdCliente());
            ps.setInt(2, chat.getIdAbogado());
            ps.setTimestamp(3, chat.getFechaInicio());
            ps.setInt(4, id);

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Error actualizando chat", e);
        }
    }

    // delete: elimina un chat por su ID
    public boolean delete(int id) {
        String query = "DELETE FROM CHAT WHERE id_chat = ?";
        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query)
        ) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Error eliminando chat", e);
        }
    }

    public List<Chat> findByNombreUsuario(String nombre) throws SQLException {
        List<Chat> chats = new ArrayList<>();
        String query = """
        SELECT c.* FROM CHAT c
        JOIN USUARIO u1 ON c.id_cliente = u1.id_usuario
        JOIN USUARIO u2 ON c.id_abogado = u2.id_usuario
        WHERE LOWER(u1.nombre) LIKE ? OR LOWER(u2.nombre) LIKE ?
        """;

        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query)
        ) {
            String pattern = "%" + nombre.toLowerCase() + "%";
            ps.setString(1, pattern);
            ps.setString(2, pattern);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Chat chat = new Chat();
                    chat.setIdChat(rs.getInt("id_chat"));
                    chat.setIdCliente(rs.getInt("id_cliente"));
                    chat.setIdAbogado(rs.getInt("id_abogado"));
                    chat.setFechaInicio(rs.getTimestamp("fecha_inicio"));
                    chats.add(chat);
                }
            }
        }
        return chats;
    }

    public List<Chat> findByUsuarioId(int idUsuario) throws SQLException {
        List<Chat> chats = new ArrayList<>();
        String query = "SELECT * FROM CHAT WHERE id_cliente = ? OR id_abogado = ?";

        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query)
        ) {
            ps.setInt(1, idUsuario);
            ps.setInt(2, idUsuario);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Chat chat = new Chat();
                    chat.setIdChat(rs.getInt("id_chat"));
                    chat.setIdCliente(rs.getInt("id_cliente"));
                    chat.setIdAbogado(rs.getInt("id_abogado"));
                    chat.setFechaInicio(rs.getTimestamp("fecha_inicio"));
                    chats.add(chat);
                }
            }
        }

        return chats;
    }

}
