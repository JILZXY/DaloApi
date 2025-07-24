package org.example.repository;

import org.example.config.DatabaseConfig;
import org.example.model.Localidad;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocalidadRepository {
    //en los findALL siempre tiene que llevar un List ya que regresara muchas tuplas que pasaran a ser objetos y se guardaran en la lista

    public List<Localidad> findAll() throws SQLException {
        List<Localidad> localidades = new ArrayList<>();
        String query = "SELECT * FROM LOCALIDAD";
        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement stmt = c.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Localidad p = new Localidad();
                p.setIdLocalidad(rs.getInt("id_localidad"));
                p.setEstado(rs.getString("estado"));
                p.setMunicipio(rs.getString("municipio"));
                localidades.add(p);
            }
        }
        return localidades;
    }

    //en los saves siempre tiene que ir un Statement.RETURN... si la id es autoincremental

    public int save(Localidad localidad) {
        String query = "INSERT INTO LOCALIDAD (estado, municipio) VALUES (?,?)";
        try (Connection c = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement ps = c.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, localidad.getEstado());
            ps.setString(2, localidad.getMunicipio());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error insertando localidad", e);
        }
        return -1;
    }

    /*en el update se usa un retorno de tipo Boolean porque es mas sencillo saber si se modifico por lo menos una sola lista y se cambian
    todos los atributos porque si fuera un PATCH por cada atributo se tendria que hacer por cada atributo individualmente (dependiendo de
    la logica de tu aplicacion se puede usar de esta forma o con PATCH pero de preferencia se usa PATCH*/

    public boolean update(int id_localidad, Localidad localidad){
        String query = "UPDATE LOCALIDAD SET estado = ?, municipio = ? WHERE id_localidad = ?";
        try (Connection c = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement ps = c.prepareStatement(query)) {

            ps.setString(1, localidad.getEstado());
            ps.setString(2, localidad.getMunicipio());
            ps.setInt(3, id_localidad);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Error actualizando localidad", e);
        }
    }

    /*mismo casa que update*/

    public boolean delete(int id_localidad){
        String query = "DELETE FROM LOCALIDAD WHERE id_localidad = ?";
        try (Connection c = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement ps = c.prepareStatement(query)) {

            ps.setInt(1,id_localidad);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Error actualizando localidad", e);
        }
    }

    public Localidad findById(int idLocalidad) throws SQLException {
        String sql = "SELECT * FROM LOCALIDAD WHERE id_localidad = ?";

        try (
                Connection conn = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, idLocalidad);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Localidad l = new Localidad();
                l.setIdLocalidad(rs.getInt("id_localidad"));
                l.setEstado(rs.getString("estado"));
                l.setMunicipio(rs.getString("municipio"));
                return l;
            }
        }

        return null; // No encontrada
    }

}
