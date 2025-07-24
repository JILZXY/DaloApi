package org.example.service;

import io.javalin.http.BadRequestResponse;
import org.example.model.MotivoReporte;
import org.example.repository.MotivoReporteRepository;

import java.sql.SQLException;
import java.util.List;

public class MotivoReporteService {
    private final MotivoReporteRepository motivoReporteRepository;

    public MotivoReporteService(MotivoReporteRepository motivoReporteRepository) {
        this.motivoReporteRepository = motivoReporteRepository;
    }

    public List<MotivoReporte> findAllMotRepo() throws SQLException{
        return motivoReporteRepository.findAll();
    }

    public int saveMotRepo(MotivoReporte motivoReporte) throws SQLException {
        if (motivoReporte.getDescripcion() == null || motivoReporte.getDescripcion().isEmpty()){
            throw new SQLException("Descripcion vacia");
        }
        String descripcion = motivoReporte.getDescripcion();
        if (FiltroLenguaje.contieneGroserias(descripcion)) {
            throw new BadRequestResponse("La descripcion contiene lenguaje inapropiado.");
        }

        return motivoReporteRepository.save(motivoReporte);
    }

    public boolean updateMotRepo(int id, MotivoReporte motivoReporte) throws SQLException {
        if (motivoReporte.getDescripcion() == null || motivoReporte.getDescripcion().isEmpty()){
            throw new SQLException("Descripcion vacia");
        }
        String descripcion = motivoReporte.getDescripcion();
        if (FiltroLenguaje.contieneGroserias(descripcion)) {
            throw new BadRequestResponse("La descripcion contiene lenguaje inapropiado.");
        }
        return motivoReporteRepository.update(id, motivoReporte);
    }

    public boolean deleteMotRepo(int id){
        return motivoReporteRepository.delete(id);
    }

}
