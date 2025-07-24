package org.example.repository;

import org.example.config.DatabaseConfig;
import org.example.model.Materia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MateriaRepository {

    // findAll: obtiene todas las materias
    public List<Materia> findAll() throws SQLException {
        List<Materia> materias = new ArrayList<>();
        String query = "SELECT * FROM MATERIA";
        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                Materia m = new Materia();
                m.setIdMateria(rs.getInt("id_materia"));
                m.setNombre(rs.getString("nombre"));
                m.setDescripcion(rs.getString("descripcion"));
                materias.add(m);
            }
        }
        return materias;
    }

    // save: inserta una nueva materia
    public int save(Materia materia) {
        String query = "INSERT INTO MATERIA (nombre, descripcion) VALUES (?, ?)";
        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, materia.getNombre());
            ps.setString(2, materia.getDescripcion());

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error insertando materia", e);
        }
        return -1;
    }

    // update: actualiza una materia existente
    public boolean update(int id, Materia materia) {
        String query = "UPDATE MATERIA SET nombre = ?, descripcion = ? WHERE id_materia = ?";
        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query)
        ) {
            ps.setString(1, materia.getNombre());
            ps.setString(2, materia.getDescripcion());
            ps.setInt(3, id);

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Error actualizando materia", e);
        }
    }

    // delete: elimina una materia por su ID
    public boolean delete(int id) {
        String query = "DELETE FROM MATERIA WHERE id_materia = ?";
        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query)
        ) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Error eliminando materia", e);
        }
    }
}

