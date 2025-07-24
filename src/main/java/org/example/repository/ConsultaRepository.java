package org.example.repository;

import org.example.config.DatabaseConfig;
import org.example.model.Consulta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultaRepository {

    // findAll: obtiene todas las consultas
    public List<Consulta> findAll() throws SQLException {
        List<Consulta> consultas = new ArrayList<>();
        String query = "SELECT * FROM CONSULTA";
        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                Consulta consulta = new Consulta();
                consulta.setIdConsulta(rs.getInt("id_consulta"));
                consulta.setIdUsuario(rs.getInt("id_usuario"));
                consulta.setTitulo(rs.getString("titulo"));
                consulta.setPregunta(rs.getString("pregunta"));
                consulta.setFechaPublicacion(rs.getTimestamp("fecha_publicacion"));
                consulta.setIdLocalidad(rs.getInt("id_localidad"));
                consulta.setIdMateria(rs.getInt("id_materia"));
                consultas.add(consulta);
            }
        }
        return consultas;
    }

    // save: inserta una nueva consulta
    public int save(Consulta consulta) {
        String query = "INSERT INTO CONSULTA (id_usuario, titulo, pregunta, fecha_publicacion, id_localidad, id_materia) VALUES (?,?,?,?,?,?)";
        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setInt(1, consulta.getIdUsuario());
            ps.setString(2, consulta.getTitulo());
            ps.setString(3, consulta.getPregunta());
            ps.setTimestamp(4, consulta.getFechaPublicacion());
            ps.setInt(5, consulta.getIdLocalidad());
            ps.setInt(6, consulta.getIdMateria());

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error insertando consulta", e);
        }
        return -1;
    }

    // update: actualiza una consulta existente
    public boolean update(int idConsulta, Consulta consulta) {
        String query = "UPDATE CONSULTA SET id_usuario = ?, titulo = ?, pregunta = ?, fecha_publicacion = ?, id_localidad = ?, id_materia = ? WHERE id_consulta = ?";
        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query)
        ) {
            ps.setInt(1, consulta.getIdUsuario());
            ps.setString(2, consulta.getTitulo());
            ps.setString(3, consulta.getPregunta());
            ps.setTimestamp(4, consulta.getFechaPublicacion());
            ps.setInt(5, consulta.getIdLocalidad());
            ps.setInt(6, consulta.getIdMateria());
            ps.setInt(7, idConsulta);

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Error actualizando consulta", e);
        }
    }

    // delete: elimina una consulta por su ID
    public boolean delete(int idConsulta) {
        String query = "DELETE FROM CONSULTA WHERE id_consulta = ?";
        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query)
        ) {
            ps.setInt(1, idConsulta);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Error eliminando consulta", e);
        }
    }

    public List<Consulta> findByEstadoAndMunicipio(String estado, String municipio) throws SQLException {
        List<Consulta> consultas = new ArrayList<>();
        String query = """
        SELECT c.* FROM CONSULTA c
        JOIN LOCALIDAD l ON c.id_localidad = l.id_localidad
        WHERE LOWER(l.estado) LIKE ? AND LOWER(l.municipio) LIKE ?
    """;
        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query)
        ) {
            ps.setString(1, "%" + estado.toLowerCase() + "%");
            ps.setString(2, "%" + municipio.toLowerCase() + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Consulta consulta = new Consulta();
                    consulta.setIdConsulta(rs.getInt("id_consulta"));
                    consulta.setIdUsuario(rs.getInt("id_usuario"));
                    consulta.setTitulo(rs.getString("titulo"));
                    consulta.setPregunta(rs.getString("pregunta"));
                    consulta.setFechaPublicacion(rs.getTimestamp("fecha_publicacion"));
                    consulta.setIdLocalidad(rs.getInt("id_localidad"));
                    consulta.setIdMateria(rs.getInt("id_materia"));
                    consultas.add(consulta);
                }
            }
        }
        return consultas;
    }

    public List<Consulta> findByNombreMateria(String nombreMateria) throws SQLException {
        List<Consulta> consultas = new ArrayList<>();
        String query = """
        SELECT c.* FROM CONSULTA c
        JOIN MATERIA m ON c.id_materia = m.id_materia
        WHERE LOWER(m.nombre) LIKE ?
    """;
        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query)
        ) {
            ps.setString(1, "%" + nombreMateria.toLowerCase() + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Consulta consulta = new Consulta();
                    consulta.setIdConsulta(rs.getInt("id_consulta"));
                    consulta.setIdUsuario(rs.getInt("id_usuario"));
                    consulta.setTitulo(rs.getString("titulo"));
                    consulta.setPregunta(rs.getString("pregunta"));
                    consulta.setFechaPublicacion(rs.getTimestamp("fecha_publicacion"));
                    consulta.setIdLocalidad(rs.getInt("id_localidad"));
                    consulta.setIdMateria(rs.getInt("id_materia"));
                    consultas.add(consulta);
                }
            }
        }
        return consultas;
    }

    public List<Consulta> findByMateriaEstadoMunicipio(String nombreMateria, String estado, String municipio) throws SQLException {
        List<Consulta> consultas = new ArrayList<>();
        String query = """
        SELECT c.* FROM CONSULTA c
        JOIN MATERIA m ON c.id_materia = m.id_materia
        JOIN LOCALIDAD l ON c.id_localidad = l.id_localidad
        WHERE LOWER(m.nombre) LIKE ? AND LOWER(l.estado) LIKE ? AND LOWER(l.municipio) LIKE ?
    """;
        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query)
        ) {
            ps.setString(1, "%" + nombreMateria.toLowerCase() + "%");
            ps.setString(2, "%" + estado.toLowerCase() + "%");
            ps.setString(3, "%" + municipio.toLowerCase() + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Consulta consulta = new Consulta();
                    consulta.setIdConsulta(rs.getInt("id_consulta"));
                    consulta.setIdUsuario(rs.getInt("id_usuario"));
                    consulta.setTitulo(rs.getString("titulo"));
                    consulta.setPregunta(rs.getString("pregunta"));
                    consulta.setFechaPublicacion(rs.getTimestamp("fecha_publicacion"));
                    consulta.setIdLocalidad(rs.getInt("id_localidad"));
                    consulta.setIdMateria(rs.getInt("id_materia"));
                    consultas.add(consulta);
                }
            }
        }
        return consultas;
    }

    public int contarConsultasPorUsuario(int idUsuario) {
        String sql = "SELECT COUNT(*) FROM CONSULTA WHERE id_usuario = ?";
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Consulta findById(int idConsulta) throws SQLException {
        String sql = "SELECT * FROM CONSULTA WHERE id_consulta = ?";

        try (
                Connection conn = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, idConsulta);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Consulta c = new Consulta();
                c.setIdConsulta(rs.getInt("id_consulta"));
                c.setIdUsuario(rs.getInt("id_usuario"));
                c.setTitulo(rs.getString("titulo"));
                c.setPregunta(rs.getString("pregunta"));
                c.setFechaPublicacion(rs.getTimestamp("fecha_publicacion"));
                c.setIdLocalidad(rs.getInt("id_localidad"));
                c.setIdMateria(rs.getInt("id_materia"));
                return c;
            }
        }

        return null; // No encontrada
    }

    public List<Consulta> findByUsuarioId(int idUsuario) throws SQLException {
        List<Consulta> consultas = new ArrayList<>();
        String query = "SELECT * FROM CONSULTA WHERE id_usuario = ?";

        try (
                Connection conn = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Consulta consulta = new Consulta();
                    consulta.setIdConsulta(rs.getInt("id_consulta"));
                    consulta.setIdUsuario(rs.getInt("id_usuario"));
                    consulta.setTitulo(rs.getString("titulo"));
                    consulta.setPregunta(rs.getString("pregunta"));
                    consulta.setFechaPublicacion(rs.getTimestamp("fecha_publicacion"));
                    consulta.setIdLocalidad(rs.getInt("id_localidad"));
                    consulta.setIdMateria(rs.getInt("id_materia"));
                    consultas.add(consulta);
                }
            }
        }

        return consultas;
    }

}

