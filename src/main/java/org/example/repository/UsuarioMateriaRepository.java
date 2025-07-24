package org.example.repository;

import org.example.config.DatabaseConfig;
import org.example.model.UsuarioMateria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioMateriaRepository {

    // findAll: obtiene todas las relaciones usuario-materia
    public List<UsuarioMateria> findAll() throws SQLException {
        List<UsuarioMateria> relaciones = new ArrayList<>();
        String query = "SELECT * FROM USUARIO_MATERIA";
        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                UsuarioMateria um = new UsuarioMateria();
                um.setIdUsuario(rs.getInt("id_usuario"));
                um.setIdMateria(rs.getInt("id_materia"));
                relaciones.add(um);
            }
        }
        return relaciones;
    }

    // save: inserta una nueva relación usuario-materia
    public int save(UsuarioMateria um) {
        String query = "INSERT INTO USUARIO_MATERIA (id_usuario, id_materia) VALUES (?, ?)";
        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query)
        ) {
            ps.setInt(1, um.getIdUsuario());
            ps.setInt(2, um.getIdMateria());
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error insertando relación usuario-materia", e);
        }
    }

    public boolean update(int idUsuarioActual, int idMateriaActual, UsuarioMateria nuevaRelacion) {
        String query = "UPDATE USUARIO_MATERIA SET id_usuario = ?, id_materia = ? WHERE id_usuario = ? AND id_materia = ?";
        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query)
        ) {
            ps.setInt(1, nuevaRelacion.getIdUsuario());
            ps.setInt(2, nuevaRelacion.getIdMateria());
            ps.setInt(3, idUsuarioActual);
            ps.setInt(4, idMateriaActual);

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Error actualizando relación usuario-materia", e);
        }
    }

    // delete: elimina una relación usuario-materia por ambos IDs
    public boolean delete(int idUsuario, int idMateria) {
        String query = "DELETE FROM USUARIO_MATERIA WHERE id_usuario = ? AND id_materia = ?";
        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query)
        ) {
            ps.setInt(1, idUsuario);
            ps.setInt(2, idMateria);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Error eliminando relación usuario-materia", e);
        }
    }
}
