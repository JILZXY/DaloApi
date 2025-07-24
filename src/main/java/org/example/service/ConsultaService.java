package org.example.service;

import io.javalin.http.BadRequestResponse;
import org.example.model.Consulta;
import org.example.repository.ConsultaRepository;

import java.sql.SQLException;
import java.util.List;

public class ConsultaService {
    private final ConsultaRepository consultaRepository;

    public ConsultaService(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    public List<Consulta> findAllConsulta() throws SQLException {
        return consultaRepository.findAll();
    }

    public int saveConsulta(Consulta consulta) throws SQLException {
        if (consulta.getPregunta() == null || consulta.getPregunta().isEmpty() ){
            throw new SQLException("La pregunta esta vacia");
        }
        if (consulta.getTitulo() == null || consulta.getTitulo().isEmpty()){
            throw new SQLException("El titulo esta vacio");
        }
        String pregunta = consulta.getPregunta();
        if (FiltroLenguaje.contieneGroserias(pregunta)) {
            throw new BadRequestResponse("La pregunta contiene lenguaje inapropiado.");
        }
        String titulo = consulta.getTitulo();
        if (FiltroLenguaje.contieneGroserias(titulo)) {
            throw new BadRequestResponse("El titulo contiene lenguaje inapropiado.");
        }
        return consultaRepository.save(consulta);
    }

    public boolean updateConsulta(int id, Consulta consulta){
        return consultaRepository.update(id, consulta);
    }

    public boolean deleteConsulta(int id){
        return consultaRepository.delete(id);
    }

    public List<Consulta> findByMAteria(String materia) throws SQLException{
        return consultaRepository.findByNombreMateria(materia);
    }

    public List<Consulta> findByMunEst(String estado, String municipio) throws SQLException{
        return consultaRepository.findByEstadoAndMunicipio(estado, municipio);
    }

    public List<Consulta> findByMatMunEst(String materia, String estado, String municipio) throws SQLException{
        return consultaRepository.findByMateriaEstadoMunicipio(materia, estado, municipio);
    }

    public  int contarConsultas(int id){
        return consultaRepository.contarConsultasPorUsuario(id);
    }

    public Consulta obtenerPorId(int idConsulta) throws SQLException {
        return consultaRepository.findById(idConsulta);
    }

    public List<Consulta> obtenerPorUsuario(int idUsuario) throws SQLException {
        return consultaRepository.findByUsuarioId(idUsuario);
    }
}
