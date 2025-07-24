package org.example.repository;

import org.example.config.DatabaseConfig;
import org.example.model.Abogado;
import org.example.model.Materia;
import org.example.model.Usuario;

import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {
    // findAll: regresa una lista de todos los usuarios
    public List<Usuario> findAll() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String query = "SELECT * FROM USUARIO";
        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement stmt = c.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("id_usuario"));
                u.setIdLocalidad(rs.getInt("id_localidad"));
                u.setIdRol(rs.getInt("id_rol"));
                u.setNombre(rs.getString("nombre"));
                u.setEmail(rs.getString("email"));
                u.setContraseña(new String(rs.getBytes("contraseña"), StandardCharsets.UTF_8));
                u.setActivo(rs.getBoolean("activo"));

                usuarios.add(u);
            }
        }
        return usuarios;
    }

    // save: inserta un nuevo usuario y regresa el ID generado
    public int save(Usuario usuario) {
        String query = "INSERT INTO USUARIO (id_localidad, id_rol, nombre, email, contraseña, activo) VALUES (?,?,?,?,?,?)";
        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setInt(1, usuario.getIdLocalidad());
            ps.setInt(2, usuario.getIdRol());
            ps.setString(3, usuario.getNombre().toLowerCase());
            ps.setString(4, usuario.getEmail());
            ps.setBytes(5, usuario.getContraseña().getBytes(StandardCharsets.UTF_8));
            ps.setBoolean(6, usuario.isActivo());

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error insertando usuario", e);
        }
        return -1;
    }

    // update: actualiza todos los campos de un usuario
    public boolean update(int id_usuario, Usuario usuario) {
        String query = "UPDATE USUARIO SET id_localidad = ?, id_rol = ?, nombre = ?, email = ?, contraseña = ?, activo = ? WHERE id_usuario = ?";
        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query)
        ) {
            ps.setInt(1, usuario.getIdLocalidad());
            ps.setInt(2, usuario.getIdRol());
            ps.setString(3, usuario.getNombre().toLowerCase());
            ps.setString(4, usuario.getEmail());
            ps.setString(5, usuario.getContraseña());
            ps.setBoolean(6, usuario.isActivo());
            ps.setInt(7, id_usuario);

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Error actualizando usuario", e);
        }
    }

    public boolean updateActive(int id_usuario, Usuario usuario) {
        String query = "UPDATE USUARIO SET activo = ? WHERE id_usuario = ?";
        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query)
        ) {
            ps.setBoolean(1, usuario.isActivo());
            ps.setInt(2, id_usuario);

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Error actualizando usuario", e);
        }
    }

    public Usuario findByIdUsuario(int id) throws SQLException {
        String query = """
        SELECT u.*, a.cedula_profesional, a.biografia, a.descripcion
        FROM USUARIO u
        LEFT JOIN ABOGADO a ON u.id_usuario = a.id_usuario
        WHERE u.id_usuario = ?
    """;

        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query)
        ) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario u = new Usuario();
                    u.setIdUsuario(rs.getInt("id_usuario"));
                    u.setIdLocalidad(rs.getInt("id_localidad"));
                    u.setIdRol(rs.getInt("id_rol"));
                    u.setNombre(rs.getString("nombre"));
                    u.setEmail(rs.getString("email"));
                    u.setContraseña(rs.getString("contraseña"));
                    u.setActivo(rs.getBoolean("activo"));

                    // Si el usuario tiene rol de abogado, instanciamos el objeto Abogado
                    if (u.esAbogado()) {
                        Abogado abogado = new Abogado();
                        abogado.setCedulaProfesional(rs.getString("cedula_profesional"));
                        abogado.setBiografia(rs.getString("biografia"));
                        abogado.setDescripcion(rs.getString("descripcion"));
                        u.setAbogado(abogado);
                    }

                    return u;
                } else {
                    throw new RuntimeException("No se encontró el usuario con ID: " + id);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al consultar el usuario por ID", e);
        }
    }

    // delete: elimina un usuario por su ID
    public boolean delete(int id_usuario) {
        String query = "DELETE FROM USUARIO WHERE id_usuario = ?";
        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query)
        ) {
            ps.setInt(1, id_usuario);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Error eliminando usuario", e);
        }
    }


    /*Busca abogados por medio de su nombre*/
    public List<Usuario> findByNameAbogados(String name) throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String query = """
        SELECT u.*, a.cedula_profesional, a.biografia, a.descripcion
        FROM USUARIO u
        JOIN ABOGADO a ON u.id_usuario = a.id_usuario
        WHERE u.id_rol = 2 AND u.activo = 1 AND LOWER(u.nombre) LIKE ?
    """;

        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query)
        ) {
            ps.setString(1, "%" + name.toLowerCase() + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Usuario u = new Usuario();
                    u.setIdUsuario(rs.getInt("id_usuario"));
                    u.setIdLocalidad(rs.getInt("id_localidad"));
                    u.setIdRol(rs.getInt("id_rol"));
                    u.setNombre(rs.getString("nombre"));
                    u.setEmail(rs.getString("email"));
                    u.setContraseña(rs.getString("contraseña"));
                    u.setActivo(rs.getBoolean("activo"));

                    Abogado abogado = new Abogado();
                    abogado.setCedulaProfesional(rs.getString("cedula_profesional"));
                    abogado.setBiografia(rs.getString("biografia"));
                    abogado.setDescripcion(rs.getString("descripcion"));

                    u.setAbogado(abogado);
                    usuarios.add(u);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar abogados por nombre", e);
        }

        return usuarios;
    }
/*Busca abogados por medio de su materia esto lo hace por medio de varios JION que juntan la tabla de usuarios que cumplan con el rol 2
* para posteriormente buscar el id de este usuario en la tabla USUARIO_MATERIA y luego buscar el id de la materia de la tabla MATERIA y asi
* la informacion del usuario*/
    public List<Usuario> findAbogadosByNombreMateria(String nombreMateria) throws SQLException {
        List<Usuario> abogados = new ArrayList<>();
        String query = """
        SELECT u.*, a.cedula_profesional, a.biografia, a.descripcion\s
        FROM USUARIO u
        JOIN USUARIO_MATERIA um ON u.id_usuario = um.id_usuario
        JOIN MATERIA m ON um.id_materia = m.id_materia
        JOIN ABOGADO a ON u.id_usuario = a.id_usuario
        WHERE u.id_rol = 2 AND u.activo = 1 AND LOWER(m.nombre) LIKE ?
    """;

        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query)
        ) {
            ps.setString(1, "%" + nombreMateria.toLowerCase() + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Usuario u = new Usuario();
                    u.setIdUsuario(rs.getInt("id_usuario"));
                    u.setNombre(rs.getString("nombre"));
                    u.setEmail(rs.getString("email"));
                    u.setIdRol(rs.getInt("id_rol"));
                    u.setIdLocalidad(rs.getInt("id_localidad"));
                    u.setActivo(rs.getBoolean("activo"));

                    if (u.esAbogado()) {
                        Abogado abogado = new Abogado();
                        abogado.setCedulaProfesional(rs.getString("cedula_profesional"));
                        abogado.setBiografia(rs.getString("biografia"));
                        abogado.setDescripcion(rs.getString("descripcion"));
                        u.setAbogado(abogado);
                    }
                    abogados.add(u);
                }
            }
        }
        return abogados;
    }
/*Trae todos los usuarios respecto a su localidad, la localidad se define con dos parametros, municipio y estado por lo que no se podra traer
* usando solo uno de estos parametros, (hace un JOIN con la tabla de localidad)*/

    public List<Usuario> findAbogadosByLocalidad(String estado, String municipio) throws SQLException {
        List<Usuario> abogados = new ArrayList<>();
        String query = """
           SELECT u.*, a.cedula_profesional, a.biografia, a.descripcion
           FROM USUARIO u
           JOIN LOCALIDAD l ON u.id_localidad = l.id_localidad
           JOIN ABOGADO a ON u.id_usuario = a.id_usuario
           WHERE u.id_rol = 2 AND u.activo = 1 AND LOWER(l.estado) LIKE ? AND LOWER(l.municipio) LIKE ?
    """;

        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query)
        ) {
            ps.setString(1, "%" + estado.toLowerCase() + "%");
            ps.setString(2, "%" + municipio.toLowerCase() + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Usuario u = new Usuario();
                    u.setIdUsuario(rs.getInt("id_usuario"));
                    u.setNombre(rs.getString("nombre"));
                    u.setEmail(rs.getString("email"));
                    u.setIdRol(rs.getInt("id_rol"));
                    u.setIdLocalidad(rs.getInt("id_localidad"));
                    u.setActivo(rs.getBoolean("activo"));

                    if (u.esAbogado()) {
                        Abogado abogado = new Abogado();
                        abogado.setCedulaProfesional(rs.getString("cedula_profesional"));
                        abogado.setBiografia(rs.getString("biografia"));
                        abogado.setDescripcion(rs.getString("descripcion"));
                        u.setAbogado(abogado);
                    }

                    abogados.add(u);
                }
            }
        }
        return abogados;
    }

/*Esto trae usuarios abogados por medio de su materia y localidad*/
    public List<Usuario> findAbogadosPorMateriaYLocalidad(String materia, String estado, String municipio) throws SQLException {
        List<Usuario> abogados = new ArrayList<>();
        String query = """
        SELECT u.*, a.cedula_profesional, a.biografia, a.descripcion
        FROM USUARIO u
        JOIN USUARIO_MATERIA um ON u.id_usuario = um.id_usuario
        JOIN MATERIA m ON um.id_materia = m.id_materia
        JOIN LOCALIDAD l ON u.id_localidad = l.id_localidad
        JOIN ABOGADO a ON u.id_usuario = a.id_usuario
        WHERE u.id_rol = 2
          AND u.activo = 1
          AND LOWER(m.nombre) LIKE ?
          AND LOWER(l.estado) LIKE ?
          AND LOWER(l.municipio) LIKE ?
    """;

        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query)
        ) {
            ps.setString(1, "%" + materia.toLowerCase() + "%");
            ps.setString(2, "%" + estado.toLowerCase() + "%");
            ps.setString(3, "%" + municipio.toLowerCase() + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Usuario u = new Usuario();
                    u.setIdUsuario(rs.getInt("id_usuario"));
                    u.setNombre(rs.getString("nombre"));
                    u.setEmail(rs.getString("email"));
                    u.setIdRol(rs.getInt("id_rol"));
                    u.setIdLocalidad(rs.getInt("id_localidad"));
                    u.setActivo(rs.getBoolean("activo"));
                    if (u.esAbogado()) {
                        Abogado abogado = new Abogado();
                        abogado.setCedulaProfesional(rs.getString("cedula_profesional"));
                        abogado.setBiografia(rs.getString("biografia"));
                        abogado.setDescripcion(rs.getString("descripcion"));
                        u.setAbogado(abogado);
                    }
                    abogados.add(u);
                }
            }
        }
        return abogados;
    }


    /*Trae solamente a un usuario (entiendase un objeto de tipo usuario) por medio de su e-mail*/
    public Usuario findByEmail(String email) throws SQLException {
        String query = "SELECT * FROM USUARIO WHERE LOWER(email) = ?";
        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query)
        ) {
            ps.setString(1, email.toLowerCase());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario u = new Usuario();
                    u.setIdUsuario(rs.getInt("id_usuario"));
                    u.setIdLocalidad(rs.getInt("id_localidad"));
                    u.setIdRol(rs.getInt("id_rol"));
                    u.setNombre(rs.getString("nombre"));
                    u.setEmail(rs.getString("email"));
                    u.setContraseña(new String(rs.getBytes("contraseña"), StandardCharsets.UTF_8));
                    u.setActivo(rs.getBoolean("activo"));
                    return u;
                }
            }
        }
        return null;
    }
/*Trae las materias con el ID del abogado, sigue una logica similar a la de buscar un abogado por su materia pero a la inversa*/
    public List<Materia> findMateriasByAbogadoId(int idAbogado) throws SQLException {
        List<Materia> materias = new ArrayList<>();
        String query = """
        SELECT m.id_materia, m.nombre, m.descripcion
        FROM MATERIA m
        JOIN USUARIO_MATERIA um ON m.id_materia = um.id_materia
        WHERE um.id_usuario = ?
    """;

        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query)
        ) {
            ps.setInt(1, idAbogado);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Materia materia = new Materia();
                    materia.setIdMateria(rs.getInt("id_materia"));
                    materia.setNombre(rs.getString("nombre"));
                    materia.setDescripcion(rs.getString("descripcion"));
                    materias.add(materia);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener materias del abogado", e);
        }

        return materias;
    }


    /*busca los usuarios inactivos "activo = 0"*/
    public List<Usuario> findByActive(){
     List<Usuario>  usuarios = new ArrayList<>();
     String query = """
        SELECT u.*, a.cedula_profesional, a.biografia, a.descripcion
        FROM USUARIO u
        LEFT JOIN ABOGADO a ON u.id_usuario = a.id_usuario
        WHERE u.activo = 0
    """;
        try (
                Connection c = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = c.prepareStatement(query);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("id_usuario"));
                u.setIdLocalidad(rs.getInt("id_localidad"));
                u.setIdRol(rs.getInt("id_rol"));
                u.setNombre(rs.getString("nombre"));
                u.setEmail(rs.getString("email"));
                u.setActivo(rs.getBoolean("activo"));
                if (u.esAbogado()) {
                    Abogado abogado = new Abogado();
                    abogado.setCedulaProfesional(rs.getString("cedula_profesional"));
                    abogado.setBiografia(rs.getString("biografia"));
                    abogado.setDescripcion(rs.getString("descripcion"));
                    u.setAbogado(abogado);
                }
                usuarios.add(u);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usuarios;
    }

    public List<Usuario> findAllAbogados() throws SQLException {
        List<Usuario> abogados = new ArrayList<>();

        String query = """
        SELECT u.*, a.cedula_profesional, a.biografia, a.descripcion
        FROM USUARIO u
        JOIN ABOGADO a ON u.id_usuario = a.id_usuario
        WHERE u.id_rol = 2
        AND u.activo = 1
    """;

        try (
                Connection conn = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("id_usuario"));
                u.setIdLocalidad(rs.getInt("id_localidad"));
                u.setIdRol(rs.getInt("id_rol"));
                u.setNombre(rs.getString("nombre"));
                u.setEmail(rs.getString("email"));
                u.setContraseña(new String(rs.getBytes("contraseña"), StandardCharsets.UTF_8));
                u.setActivo(rs.getBoolean("activo"));

                // Datos del abogado
                Abogado abogado = new Abogado();
                abogado.setCedulaProfesional(rs.getString("cedula_profesional"));
                abogado.setBiografia(rs.getString("biografia"));
                abogado.setDescripcion(rs.getString("descripcion"));

                u.setAbogado(abogado);

                abogados.add(u);
            }
        }

        return abogados;
    }


    public Usuario findAbogadoById(int idUsuario) throws SQLException {
        String sql = """
        SELECT u.*, a.cedula_profesional, a.biografia, a.descripcion
        FROM USUARIO u
        JOIN ABOGADO a ON u.id_usuario = a.id_usuario
        WHERE u.id_usuario = ? AND u.id_rol = 2
    """;

        try (
                Connection conn = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("id_usuario"));
                u.setIdLocalidad(rs.getInt("id_localidad"));
                u.setIdRol(rs.getInt("id_rol"));
                u.setNombre(rs.getString("nombre"));
                u.setEmail(rs.getString("email"));
                u.setContraseña(new String(rs.getBytes("contraseña"), StandardCharsets.UTF_8));
                u.setActivo(rs.getBoolean("activo"));

                Abogado abogado = new Abogado();
                abogado.setCedulaProfesional(rs.getString("cedula_profesional"));
                abogado.setBiografia(rs.getString("biografia"));
                abogado.setDescripcion(rs.getString("descripcion"));

                u.setAbogado(abogado);
                return u;
            }
        }

        return null; // no encontrado
    }


}
