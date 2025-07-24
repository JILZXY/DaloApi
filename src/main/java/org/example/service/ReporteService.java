package org.example.service;

import org.example.model.Reporte;
import org.example.repository.ReporteRepository;

import java.sql.SQLException;
import java.util.List;

public class ReporteService {
    private final ReporteRepository reporteRepository;

    public ReporteService(ReporteRepository reporteRepository) {
        this.reporteRepository = reporteRepository;
    }

    public List<Reporte> findAllReporte() throws SQLException{
        return reporteRepository.findAll();
    }

    public int saveReporter(Reporte reporte) throws SQLException {
        return reporteRepository.save(reporte);
    }

    public boolean updateReporte(int id, Reporte reporte) throws SQLException {
        return reporteRepository.update(id, reporte);
    }

    public boolean deleteReporte(int id) throws SQLException{
        return reporteRepository.delete(id);
    }
}
