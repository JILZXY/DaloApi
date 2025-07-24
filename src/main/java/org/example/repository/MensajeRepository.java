package org.example.repository;

import org.example.config.DatabaseConfig;
import org.example.model.Mensaje;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MensajeRepository {

    // findAll: obtiene todos los mensajes
    public List<Mensaje> findAll() throws SQLException {
        List<Mensaje> mensajes = new ArrayList<>();
        String query = "SELECT * FROM MENSAJE";
        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                Mensaje m = new Mensaje();
                m.setIdMensaje(rs.getInt("id_mensaje"));
                m.setIdChat(rs.getInt("id_chat"));
                m.setIdRemitente(rs.getInt("id_remitente"));
                m.setMensaje(rs.getString("mensaje"));
                m.setFechaEnvio(rs.getTimestamp("fecha_envio"));
                mensajes.add(m);
            }
        }
        return mensajes;
    }

    // save: inserta un nuevo mensaje
    public int save(Mensaje mensaje) {
        String query = "INSERT INTO MENSAJE (id_chat, id_remitente, mensaje, fecha_envio) VALUES (?, ?, ?, ?)";
        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setInt(1, mensaje.getIdChat());
            ps.setInt(2, mensaje.getIdRemitente());
            ps.setString(3, mensaje.getMensaje());
            ps.setTimestamp(4, mensaje.getFechaEnvio());

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error insertando mensaje", e);
        }
        return -1;
    }

    // update: actualiza un mensaje existente
    public boolean update(int id, Mensaje mensaje) {
        String query = "UPDATE MENSAJE SET id_chat = ?, id_remitente = ?, mensaje = ?, fecha_envio = ? WHERE id_mensaje = ?";
        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query)
        ) {
            ps.setInt(1, mensaje.getIdChat());
            ps.setInt(2, mensaje.getIdRemitente());
            ps.setString(3, mensaje.getMensaje());
            ps.setTimestamp(4, mensaje.getFechaEnvio());
            ps.setInt(5, id);

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Error actualizando mensaje", e);
        }
    }

    // delete: elimina un mensaje por su ID
    public boolean delete(int id) {
        String query = "DELETE FROM MENSAJE WHERE id_mensaje = ?";
        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query)
        ) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Error eliminando mensaje", e);
        }
    }

    public List<Mensaje> findByChatId(int idChat) throws SQLException {
        List<Mensaje> mensajes = new ArrayList<>();
        String query = "SELECT * FROM MENSAJE WHERE id_chat = ? ORDER BY fecha_envio ASC";

        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query)
        ) {
            ps.setInt(1, idChat);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Mensaje m = new Mensaje();
                    m.setIdMensaje(rs.getInt("id_mensaje"));
                    m.setIdChat(rs.getInt("id_chat"));
                    m.setIdRemitente(rs.getInt("id_remitente"));
                    m.setMensaje(rs.getString("mensaje"));
                    m.setFechaEnvio(rs.getTimestamp("fecha_envio"));
                    mensajes.add(m);
                }
            }
        }

        return mensajes;
    }
}

