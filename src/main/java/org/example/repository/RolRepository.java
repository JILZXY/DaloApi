package org.example.repository;

import org.example.config.DatabaseConfig;
import org.example.model.Rol;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RolRepository {

    //en los findALL siempre tiene que llevar un List ya que regresara muchas tuplas que pasaran a ser objetos y se guardaran en la lista
    public List<Rol> findAll() throws SQLException {
        List<Rol> rols = new ArrayList<>();
        String query = "SELECT * FROM ROL";
        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement stmt = c.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Rol p = new Rol();
                p.setId_rol(rs.getInt("id_rol"));
                p.setNombre(rs.getString("nombre"));
                rols.add(p);
            }
        }
        return rols;
    }
    //en los saves siempre tiene que ir un Statement.RETURN... si la id es autoincremental
    public int save(Rol rol) {
        String query = "INSERT INTO ROL (nombre) VALUES (?)";
        try (Connection c = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement ps = c.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, rol.getNombre());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error insertando rol", e);
        }
        return -1;
    }
    /*en el udate se usa un retorno de tipo Boolean porque es mas sencillo saber si se modifico por lo menos una sola lista y se cambian
    todos los atributos porque si fuera un PATCH por cada atributo se tendria que hacer por cada atributo individualmente (dependiendo de
    la logica de tu aplicacion se puede usar de esta forma o con PATCH pero de preferencia se usa PATCH*/
    public boolean update(int id_rol, Rol rol){
        String query = "UPDATE ROL SET nombre = ? WHERE id_rol = ?";
        try (Connection c = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement ps = c.prepareStatement(query)) {

            ps.setString(1, rol.getNombre());
            ps.setInt(2, id_rol);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Error actualizando rol", e);
        }
    }
    /*mismo casa que update*/
    public boolean delete(int id_rol){
        String query = "DELETE FROM ROL WHERE id_rol = ?";
        try (Connection c = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement ps = c.prepareStatement(query)) {

            ps.setInt(1,id_rol);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Error actualizando rol", e);
        }
    }

}
