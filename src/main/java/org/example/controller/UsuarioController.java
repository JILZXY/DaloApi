package org.example.controller;

import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.http.InternalServerErrorResponse;
import io.javalin.http.NotFoundResponse;
import org.example.model.LoginRequest;
import org.example.model.LoginResponse;
import org.example.model.Materia;
import org.example.model.Usuario;
import org.example.service.UsuarioService;

import java.sql.SQLException;
import java.util.List;

public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }


    public void findAllUsuario(Context ctx){
        try{
            List<Usuario> Usuario = usuarioService.findAllUsuario();
            ctx.json(Usuario);
        }catch (SQLException e){
            ctx.status(500).result("Error al obtener el usuario");
        }
    }

    public void createUsuario(Context ctx){
        try {
            Usuario usuario = ctx.bodyAsClass(Usuario.class);
            int idGenerado = usuarioService.saveUsuario(usuario);
            ctx.status(200).json(idGenerado);
        }catch (SQLException e){
            ctx.status(500).result("Error al crear el usuario");
        }
    }

    public void updateUsuario(Context ctx){
        Usuario usuario = ctx.bodyAsClass(Usuario.class);
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        try {
            if (usuarioService.updateUsuario(id, usuario)) {
                ctx.status(204);
            } else {
                throw new NotFoundResponse("usuario no encontrado");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateUsuarioSinContraseña(Context ctx) {
        Usuario usuario = ctx.bodyAsClass(Usuario.class);
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        try {
            if (usuarioService.updateAct(id, usuario)) {
                ctx.status(204); // No Content, actualización exitosa
            } else {
                throw new NotFoundResponse("Usuario no encontrado");
            }
        } catch (BadRequestResponse e) {
            throw e; // Se lanza directamente para que Javalin devuelva 400
        } catch (SQLException e) {
            throw new InternalServerErrorResponse("Error al actualizar usuario: " + e.getMessage());
        }
    }


    // Método para login
    public void login(Context ctx) {
        try {
            // Obtener datos del request
            LoginRequest loginRequest = ctx.bodyAsClass(LoginRequest.class);

            // Validar datos de entrada
            if (loginRequest.getEmail() == null || loginRequest.getEmail().trim().isEmpty()) {
                ctx.status(400).json(new LoginResponse(false, "Email es requerido", null));
                return;
            }

            if (loginRequest.getContraseña() == null || loginRequest.getContraseña().trim().isEmpty()) {
                ctx.status(400).json(new LoginResponse(false, "Contraseña es requerida", null));
                return;
            }

            // Intentar login
            LoginResponse response = usuarioService.login(
                    loginRequest.getEmail().trim(),
                    loginRequest.getContraseña()
            );

            if (response.isSuccess()) {
                // Login exitoso
                ctx.status(200).json(response);
            } else {
                // Login fallido
                ctx.status(401).json(response);
            }

        } catch (Exception e) {
            ctx.status(500).json(new LoginResponse(false, "Error interno del servidor", null));
        }
    }

    public void deleteUsuario(Context ctx){
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        if (usuarioService.deleteUsuario(id)) {
            ctx.status(204);
        } else {
            throw new NotFoundResponse("usuario no encontrado");
        }
    }

    public void findByNameAbogados(Context ctx){
        try{
            String name = ctx.pathParam("nombre").toLowerCase();
            List<Usuario> abogados = usuarioService.findByNameAbogado(name);
            if (abogados.isEmpty()) {
                ctx.status(404).result("No se encontraron abogados con ese nombre.");
            } else {
                ctx.json(abogados);
            }
        }catch (SQLException e){
            ctx.status(500).result("Error al buscar el abogado");
        }
    }

    public void findAbogadosByMateria(Context ctx) {
        String nombreMateria = ctx.queryParam("nombre");
        if (nombreMateria == null || nombreMateria.isBlank()) {
            ctx.status(400).result("El parámetro 'nombre' es obligatorio");
            return;
        }

        try {
            List<Usuario> abogados = usuarioService.findByMateria(nombreMateria);
            ctx.json(abogados);
        } catch (SQLException e) {
            ctx.status(500).result("Error al buscar abogados por materia");
        }
    }

    public void findAbogadosByLocalidad(Context ctx) {
        String estado = ctx.queryParam("estado");
        String municipio = ctx.queryParam("municipio");

        if (estado == null || municipio == null) {
            ctx.status(400).result("Faltan parámetros: estado y municipio son requeridos");
            return;
        }

        try {
            List<Usuario> abogados = usuarioService.findByLocalidad(estado.toLowerCase(), municipio.toLowerCase());
            ctx.json(abogados);
        } catch (SQLException e) {
            ctx.status(500).result("Error al buscar abogados por localidad");
        }
    }

    public void findAbogadosPorMateriaYLocalidad(Context ctx) {
        String materia = ctx.queryParam("materia");
        String estado = ctx.queryParam("estado");
        String municipio = ctx.queryParam("municipio");

        if (materia == null || estado == null || municipio == null) {
            ctx.status(400).result("Faltan parámetros: materia, estado y municipio son requeridos");
            return;
        }

        try {
            List<Usuario> abogados = usuarioService.findAbogadosPorMateriaYLocalidad(materia, estado, municipio);
            ctx.json(abogados);
        } catch (SQLException e) {
            ctx.status(500).result("Error al buscar abogados por materia y localidad");
        }
    }

    public void findByEmail(Context ctx){
        String email = ctx.pathParam("email");

        if (email == null){
            ctx.status(400).result("Falta el email");
            return;
        }

        try {
            Usuario usuario = usuarioService.findByEmail(email);
            if (usuario == null) {
                ctx.status(404).result("Usuario no encontrado");
            } else {
                ctx.json(usuario);
            }
        } catch (SQLException e){
            ctx.status(500).result("Error al buscar el email");
        }
    }

    public void findMatByIdAbo(Context ctx){
        int idAbogado = ctx.pathParamAsClass("id", Integer.class).get();

        try {
            List<Materia> materias = usuarioService.findMatByAbo(idAbogado);
            ctx.json(materias);
        } catch (SQLException e) {
            ctx.status(500).result("Error al obtener materias del abogado");
        }
    }

    public void findByActive(Context ctx){
        try{
            List<Usuario> usuarios = usuarioService.findByActive();
            ctx.json(usuarios);
        } catch (SQLException e) {
            ctx.status(500).result("Error al buscar abogados inactivos");
        }
    }

    public void obtenerAbogados(Context ctx) {
        try {
            List<Usuario> abogados = usuarioService.obtenerTodosLosAbogados();
            ctx.json(abogados);
        } catch (SQLException e) {
            ctx.status(500).result("Error al obtener los abogados");
        }
    }

    public void obtenerAbogadoPorId(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        try {
            Usuario abogado = usuarioService.obtenerAbogadoPorId(id);
            if (abogado != null) {
                ctx.json(abogado);
            } else {
                ctx.status(404).result("Abogado no encontrado");
            }
        } catch (SQLException e) {
            ctx.status(500).result("Error al obtener el abogado");
        }
    }

    public void findByIdUsuario(Context ctx){
        int id = ctx.pathParamAsClass("id", Integer.class).get();

        try {
            Usuario usuario = usuarioService.findByIdUsuario(id);
            if (usuario != null) {
                ctx.json(usuario);
            } else {
                ctx.status(404).result("Usuario no encontrado");
            }
        } catch (SQLException e) {
            ctx.status(500).result("Error al obtener el usuario");
        }
    }

}
