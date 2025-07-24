package org.example.repository;

import org.example.model.Abogado;
import org.example.config.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class AbogadoRepository {

    // Obtener todos los abogados
    public List<Abogado> findAll() throws SQLException {
        List<Abogado> abogados = new ArrayList<>();
        String query = "SELECT * FROM ABOGADO";

        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                Abogado a = new Abogado();
                a.setIdUsuario(rs.getInt("id_usuario"));
                a.setCedulaProfesional(rs.getString("cedula_profesional"));
                a.setBiografia(rs.getString("biografia"));
                a.setDescripcion(rs.getString("descripcion"));
                abogados.add(a);
            }
        }

        return abogados;
    }

    // Insertar nuevo registro en la tabla ABOGADO
    public int save(Abogado abogado) {
        String query = """
        INSERT INTO ABOGADO (id_usuario, cedula_profesional, biografia, descripcion)
        VALUES (?, ?, ?, ?)
    """;

        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query)
        ) {
            ps.setInt(1, abogado.getIdUsuario());
            ps.setString(2, abogado.getCedulaProfesional());
            ps.setString(3, abogado.getBiografia());
            ps.setString(4, abogado.getDescripcion());

            int rowsInserted = ps.executeUpdate();
            return rowsInserted == 1 ? abogado.getIdUsuario() : -1;
        } catch (SQLException e) {
            throw new RuntimeException("Error insertando abogado", e);
        }
    }


    // Actualizar datos del abogado
    public boolean update(int id_usuario, Abogado abogado) throws SQLException {
        String query = """
            UPDATE ABOGADO
            SET cedula_profesional = ?, biografia = ?, descripcion = ?
            WHERE id_usuario = ?
        """;

        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query)
        ) {
            ps.setString(1, abogado.getCedulaProfesional());
            ps.setString(2, abogado.getBiografia());
            ps.setString(3, abogado.getDescripcion());
            ps.setInt(4, id_usuario);

            return ps.executeUpdate() == 1;
        }
    }

    // Eliminar abogado por ID
    public boolean delete(int id_usuario) throws SQLException {
        String query = "DELETE FROM ABOGADO WHERE id_usuario = ?";

        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query)
        ) {
            ps.setInt(1, id_usuario);
            return ps.executeUpdate() == 1;
        }
    }
}
