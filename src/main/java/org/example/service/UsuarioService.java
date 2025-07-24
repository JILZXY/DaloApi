package org.example.service;

import io.javalin.http.BadRequestResponse;
import org.example.model.LoginResponse;
import org.example.model.Materia;
import org.example.model.Usuario;
import org.example.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.SQLException;
import java.util.List;

public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder encoder;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.encoder = new BCryptPasswordEncoder();
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> findAllUsuario() throws SQLException{
        return usuarioRepository.findAll();
    }

    public List<Usuario> findByNameAbogado(String name) throws SQLException {
        return usuarioRepository.findByNameAbogados(name);
    }

    public int saveUsuario(Usuario usuario) throws SQLException{
        if(usuario.getContraseña() == null || usuario.getContraseña().isEmpty()) {
            throw new SQLException("La contraseña no es valida");
        } else {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String hashedPassword = encoder.encode(usuario.getContraseña());
            usuario.setContraseña(hashedPassword);
        }
        if (usuario.getEmail() == null || usuario.getEmail().isEmpty()){
            throw new SQLException("El correo no es valido");
        }
        if (usuario.getNombre() == null || usuario.getNombre().isEmpty()){
            throw new SQLException("El nombre no es valido");
        }
        String nombre = usuario.getNombre();
        if (FiltroLenguaje.contieneGroserias(nombre)) {
            throw new BadRequestResponse("El nombre contiene lenguaje inapropiado.");
        }
        return usuarioRepository.save(usuario);
    }

    public boolean updateUsuario(int idUsuario, Usuario usuario) throws SQLException {
        if(usuario.getContraseña() == null || usuario.getContraseña().isEmpty()) {
            throw new SQLException("La contraseña no es valida");
        } else {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String hashedPassword = encoder.encode(usuario.getContraseña());
            usuario.setContraseña(hashedPassword);
        }
        if (usuario.getEmail() == null || usuario.getEmail().isEmpty()){
            throw new SQLException("El correo no es valido");
        }
        if (usuario.getNombre() == null || usuario.getNombre().isEmpty()){
            throw new SQLException("El nombre no es valido");
        }
        String nombre = usuario.getNombre();
        if (FiltroLenguaje.contieneGroserias(nombre)) {
            throw new BadRequestResponse("El nombre contiene lenguaje inapropiado.");
        }
        return usuarioRepository.update(idUsuario, usuario);
    }

    public LoginResponse login(String email, String contraseña) {
        try {
            // Buscar usuario por email
            Usuario usuario = usuarioRepository.findByEmail(email);

            if (usuario == null) {
                return new LoginResponse(false, "Usuario no encontrado", null);
            }

            // Verificar si el usuario está activo
            if (!usuario.isActivo()) {
                return new LoginResponse(false, "Usuario inactivo", null);
            }

            // Verificar contraseña usando BCrypt
            if (!encoder.matches(contraseña, usuario.getContraseña())) {
                return new LoginResponse(false, "Contraseña incorrecta", null);
            }

            // Login exitoso - quitar contraseña antes de devolver por seguridad
            usuario.setContraseña(null);

            return new LoginResponse(true, "Login exitoso", usuario);

        } catch (Exception e) {
            return new LoginResponse(false, "Error en el servidor: " + e.getMessage(), null);
        }
    }

    public boolean deleteUsuario(int idUsuario){
        return usuarioRepository.delete(idUsuario);
    }

    public List<Usuario> findByMateria(String materia) throws SQLException{
        return usuarioRepository.findAbogadosByNombreMateria(materia);
    }

    public List<Usuario> findByLocalidad(String estado, String municipio) throws SQLException{
        return usuarioRepository.findAbogadosByLocalidad(estado, municipio);
    }

    public List<Usuario> findAbogadosPorMateriaYLocalidad(String materia, String estado, String municipio) throws SQLException {
        return usuarioRepository.findAbogadosPorMateriaYLocalidad(materia, estado, municipio);
    }

    public Usuario findByEmail(String email) throws SQLException {
        return usuarioRepository.findByEmail(email);
    }

    public  List<Materia> findMatByAbo(int id) throws SQLException {
        return usuarioRepository.findMateriasByAbogadoId(id);
    }

    public  List<Usuario> findByActive() throws SQLException {
        return usuarioRepository.findByActive();
    }

    public List<Usuario> obtenerTodosLosAbogados() throws SQLException {
        return usuarioRepository.findAllAbogados();
    }

    public Usuario obtenerAbogadoPorId(int idUsuario) throws SQLException {
        return usuarioRepository.findAbogadoById(idUsuario);
    }


    public boolean updateAct(int idUsuario, Usuario usuario) throws SQLException {
        return usuarioRepository.updateActive(idUsuario, usuario);
    }

    public Usuario findByIdUsuario(int id) throws SQLException {
        return usuarioRepository.findByIdUsuario(id);
    }

}
