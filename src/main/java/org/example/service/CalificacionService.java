package org.example.service;

import org.example.model.Calificacion;
import org.example.repository.CalificacionRepository;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class CalificacionService {
    private final CalificacionRepository calificacionRepository;

    public CalificacionService(CalificacionRepository calificacionRepository) {
        this.calificacionRepository = calificacionRepository;
    }

    public List<Calificacion> finAllCalificacion () throws SQLException {
        return calificacionRepository.findAll();
    }

    public int saveCalificacion(Calificacion calificacion){
        return calificacionRepository.save(calificacion);
    }

    public boolean updateCalificacion(int idCalificacion, Calificacion calificacion){
        return calificacionRepository.update(idCalificacion, calificacion);
    }

    public boolean deleteCalificacion(int idCalificacion){
        return calificacionRepository.delete(idCalificacion);
    }



    public Map<String, BigDecimal> promedioAbogado(int id) throws SQLException {
        return calificacionRepository.promedioAbogado(id);
    }

    public Map<String, BigDecimal> promedioRespuesta(int id) throws SQLException{
     return calificacionRepository.promediosPorRespuesta(id);
    }
}
