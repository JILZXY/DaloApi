package org.example.service;

import org.example.model.Materia;
import org.example.repository.MateriaRepository;

import java.sql.SQLException;
import java.util.List;

public class MateriaService {
    private final MateriaRepository materiaRepository;

    public MateriaService(MateriaRepository materiaRepository) {
        this.materiaRepository = materiaRepository;
    }

    public List<Materia> findAllMAteria() throws SQLException{
        return materiaRepository.findAll();
    }

    public int saveMateria(Materia materia) {

        return materiaRepository.save(materia);
    }

    public boolean updateMateria(int id, Materia materia){
        return materiaRepository.update(id, materia);
    }

    public boolean deleteMateria(int id){
        return materiaRepository.delete(id);
    }

}
