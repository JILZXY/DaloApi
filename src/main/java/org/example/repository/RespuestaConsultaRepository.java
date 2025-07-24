package org.example.repository;

import org.example.config.DatabaseConfig;
import org.example.model.RespuestaConsulta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RespuestaConsultaRepository {

    // findAll: obtiene todas las respuestas
    public List<RespuestaConsulta> findAll() throws SQLException {
        List<RespuestaConsulta> respuestas = new ArrayList<>();
        String query = "SELECT * FROM RESPUESTA_CONSULTA";
        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                RespuestaConsulta r = new RespuestaConsulta();
                r.setIdRespuesta(rs.getInt("id_respuesta"));
                r.setIdUsuarioAbogado(rs.getInt("id_usuario_abogado"));
                r.setIdConsulta(rs.getInt("id_consulta"));
                r.setRespuesta(rs.getString("respuesta"));
                r.setFechaRespuesta(rs.getTimestamp("fecha_respuesta"));
                respuestas.add(r);
            }
        }
        return respuestas;
    }

    // save: inserta una nueva respuesta
    public int save(RespuestaConsulta respuesta) {
        String query = "INSERT INTO RESPUESTA_CONSULTA (id_usuario_abogado, id_consulta, respuesta, fecha_respuesta) VALUES (?, ?, ?, ?)";
        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setInt(1, respuesta.getIdUsuarioAbogado());
            ps.setInt(2, respuesta.getIdConsulta());
            ps.setString(3, respuesta.getRespuesta());
            ps.setTimestamp(4, respuesta.getFechaRespuesta());

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error insertando respuesta", e);
        }
        return -1;
    }

    // update: actualiza una respuesta existente
    public boolean update(int id, RespuestaConsulta respuesta) {
        String query = "UPDATE RESPUESTA_CONSULTA SET id_usuario_abogado = ?, id_consulta = ?, respuesta = ?, fecha_respuesta = ? WHERE id_respuesta = ?";
        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query)
        ) {
            ps.setInt(1, respuesta.getIdUsuarioAbogado());
            ps.setInt(2, respuesta.getIdConsulta());
            ps.setString(3, respuesta.getRespuesta());
            ps.setTimestamp(4, respuesta.getFechaRespuesta());
            ps.setInt(5, id);

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Error actualizando respuesta", e);
        }
    }

    // delete: elimina una respuesta por su ID
    public boolean delete(int id) {
        String query = "DELETE FROM RESPUESTA_CONSULTA WHERE id_respuesta = ?";
        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query)
        ) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Error eliminando respuesta", e);
        }
    }

    public List<RespuestaConsulta> findByIdConsulta(int idConsulta) throws SQLException {
        List<RespuestaConsulta> respuestas = new ArrayList<>();
        String query = "SELECT * FROM RESPUESTA_CONSULTA WHERE id_consulta = ?";

        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query)
        ) {
            ps.setInt(1, idConsulta);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RespuestaConsulta r = new RespuestaConsulta();
                    r.setIdRespuesta(rs.getInt("id_respuesta"));
                    r.setIdUsuarioAbogado(rs.getInt("id_usuario_abogado"));
                    r.setIdConsulta(rs.getInt("id_consulta"));
                    r.setRespuesta(rs.getString("respuesta"));
                    r.setFechaRespuesta(rs.getTimestamp("fecha_respuesta"));
                    respuestas.add(r);
                }
            }
        }
        return respuestas;
    }

    public int contarRespuestasPorAbogado(int idAbogado) {
        String sql = "SELECT COUNT(*) FROM RESPUESTA_CONSULTA WHERE id_usuario_abogado = ?";
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idAbogado);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<RespuestaConsulta> findByIdUsuarioAbogado(int idUsuarioAbogado) throws SQLException {
        List<RespuestaConsulta> respuestas = new ArrayList<>();
        String query = "SELECT * FROM RESPUESTA_CONSULTA WHERE id_usuario_abogado = ?";

        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query)
        ) {
            ps.setInt(1, idUsuarioAbogado);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RespuestaConsulta r = new RespuestaConsulta();
                    r.setIdRespuesta(rs.getInt("id_respuesta"));
                    r.setIdUsuarioAbogado(rs.getInt("id_usuario_abogado"));
                    r.setIdConsulta(rs.getInt("id_consulta"));
                    r.setRespuesta(rs.getString("respuesta"));
                    r.setFechaRespuesta(rs.getTimestamp("fecha_respuesta"));
                    respuestas.add(r);
                }
            }
        }

        return respuestas;
    }

}

