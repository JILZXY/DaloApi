package org.example.repository;

import org.example.config.DatabaseConfig;
import org.example.model.MotivoReporte;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MotivoReporteRepository {

    // findAll: obtiene todos los motivos de reporte
    public List<MotivoReporte> findAll() throws SQLException {
        List<MotivoReporte> motivos = new ArrayList<>();
        String query = "SELECT * FROM MOTIVO_REPORTE";
        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                MotivoReporte motivo = new MotivoReporte();
                motivo.setIdMotivoReporte(rs.getInt("id_motivo_reporte"));
                motivo.setNombre(rs.getString("nombre"));
                motivo.setDescripcion(rs.getString("descripcion"));
                motivos.add(motivo);
            }
        }
        return motivos;
    }

    // save: inserta un nuevo motivo de reporte
    public int save(MotivoReporte motivoReporte) {
        String query = "INSERT INTO MOTIVO_REPORTE (nombre, descripcion) VALUES (?, ?)";
        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, motivoReporte.getNombre());
            ps.setString(2, motivoReporte.getDescripcion());

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error insertando motivo de reporte", e);
        }
        return -1;
    }

    // update: actualiza un motivo existente
    public boolean update(int id, MotivoReporte motivo) {
        String query = "UPDATE MOTIVO_REPORTE SET nombre = ?, descripcion = ? WHERE id_motivo_reporte = ?";
        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query)
        ) {
            ps.setString(1, motivo.getNombre());
            ps.setString(2, motivo.getDescripcion());

            ps.setInt(4, id);

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Error actualizando motivo de reporte", e);
        }
    }

    // delete: elimina un motivo por su ID
    public boolean delete(int id) {
        String query = "DELETE FROM MOTIVO_REPORTE WHERE id_motivo_reporte = ?";
        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query)
        ) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Error eliminando motivo de reporte", e);
        }
    }
}

