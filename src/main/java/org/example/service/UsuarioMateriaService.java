package org.example.service;

import org.example.model.Materia;
import org.example.model.UsuarioMateria;
import org.example.repository.UsuarioMateriaRepository;

import java.sql.SQLException;
import java.util.List;

public class UsuarioMateriaService {
    private final UsuarioMateriaRepository usuarioMateriaRepository;

    public UsuarioMateriaService(UsuarioMateriaRepository usuarioMateriaRepository) {
        this.usuarioMateriaRepository = usuarioMateriaRepository;
    }


    public List<UsuarioMateria> findAllUserMateria() throws SQLException {
        return usuarioMateriaRepository.findAll();
    }

    public int saveUserMateria(UsuarioMateria userMateria) {
        return usuarioMateriaRepository.save(userMateria);
    }

    public boolean updateUserMateria(int id, int idMateria, UsuarioMateria usuarioMateria){
        return usuarioMateriaRepository.update(id, idMateria, usuarioMateria);
    }

    public boolean deleteUserMateria(int id, int idMateria){
        return usuarioMateriaRepository.delete(id, idMateria);
    }
}
