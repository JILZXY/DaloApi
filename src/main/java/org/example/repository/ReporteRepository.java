package org.example.repository;

import org.example.config.DatabaseConfig;
import org.example.model.Reporte;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReporteRepository {
    // findAll: obtiene todos los reportes
    public List<Reporte> findAll() throws SQLException {
        List<Reporte> reportes = new ArrayList<>();
        String query = "SELECT * FROM REPORTE";
        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                Reporte r = new Reporte();
                r.setIdReporte(rs.getInt("id_reporte"));
                r.setIdUsuarioReporta(rs.getInt("id_usuario_reporta"));
                r.setIdUsuarioReportado(rs.getInt("id_usuario_reportado"));
                r.setIdMotivoReporte(rs.getInt("id_motivo_reporte"));
                r.setIdConsulta(rs.getInt("id_consulta"));
                r.setFechaReporte(rs.getTimestamp("fecha_reporte"));
                r.setComentarios(rs.getString("comentarios"));
                reportes.add(r);
            }
        }
        return reportes;
    }

    // save: inserta un nuevo reporte
    public int save(Reporte reporte) {
        String query = "INSERT INTO REPORTE (id_usuario_reporta, id_usuario_reportado, id_motivo_reporte, id_consulta, fecha_reporte, comentarios) VALUES (?, ?, ?, ?, ?, ?)";
        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setInt(1, reporte.getIdUsuarioReporta());
            ps.setInt(2, reporte.getIdUsuarioReportado());
            ps.setInt(3, reporte.getIdMotivoReporte());
            ps.setInt(4, reporte.getIdConsulta());
            ps.setTimestamp(5, reporte.getFechaReporte());
            ps.setString(6, reporte.getComentarios());

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error SQL: " + e.getMessage());
            throw new RuntimeException("Error insertando reporte", e);

        }
        return -1;
    }

    // update: actualiza un reporte existente
    public boolean update(int id, Reporte reporte) {
        String query = "UPDATE REPORTE SET id_usuario_reporta = ?, id_usuario_reportado = ?, id_motivo_reporte = ?, id_consulta = ?, fecha_reporte = ?, comentarios = ? WHERE id_reporte = ?";
        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query)
        ) {
            ps.setInt(1, reporte.getIdUsuarioReporta());
            ps.setInt(2, reporte.getIdUsuarioReportado());
            ps.setInt(3, reporte.getIdMotivoReporte());
            ps.setInt(4, reporte.getIdConsulta());
            ps.setTimestamp(5, reporte.getFechaReporte());
            ps.setString(6, reporte.getComentarios());
            ps.setInt(7, id);

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Error actualizando reporte", e);
        }
    }

    // delete: elimina un reporte por su ID
    public boolean delete(int id) {
        String query = "DELETE FROM REPORTE WHERE id_reporte = ?";
        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query)
        ) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Error eliminando reporte", e);
        }
    }
}
