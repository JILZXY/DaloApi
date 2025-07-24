package org.example.service;

import io.javalin.http.BadRequestResponse;
import org.example.model.Mensaje;
import org.example.repository.MensajeRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class MensajeService {
    private final MensajeRepository mensajeRepository;

    public MensajeService(MensajeRepository mensajeRepository) {
        this.mensajeRepository = mensajeRepository;
    }


    public List<Mensaje> findAllMensajes() throws SQLException{
        return mensajeRepository.findAll();
    }

    public int saveMensaje(Mensaje mensaje) throws SQLException {
        if (mensaje.getMensaje() == null || mensaje.getMensaje().isEmpty()){
            throw new SQLException("Mensaje vacio");
        }
        String descripcion = mensaje.getMensaje();
        if (FiltroLenguaje.contieneGroserias(descripcion)) {
            throw new BadRequestResponse("El mensaje contiene lenguaje inapropiado.");
        }
        return mensajeRepository.save(mensaje);
    }

    public boolean updateMensaje(int id, Mensaje mensaje) throws SQLException {
        if (mensaje.getMensaje() == null || mensaje.getMensaje().isEmpty()){
            throw new SQLException("Mensaje vacio");
        }
        String descripcion = mensaje.getMensaje();
        if (FiltroLenguaje.contieneGroserias(descripcion)) {
            throw new BadRequestResponse("El mensaje contiene lenguaje inapropiado.");
        }
        return mensajeRepository.update(id, mensaje);
    }

    public boolean deleteMensaje(int id){
        return mensajeRepository.delete(id);
    }

    public List<Mensaje> findMensajesPorChat(int idChat) throws SQLException {
        return mensajeRepository.findByChatId(idChat);
    }
}
