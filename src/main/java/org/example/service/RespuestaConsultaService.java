package org.example.service;

import io.javalin.http.BadRequestResponse;
import org.example.model.RespuestaConsulta;
import org.example.repository.RespuestaConsultaRepository;

import java.sql.SQLException;
import java.util.List;

public class RespuestaConsultaService {
    private  final RespuestaConsultaRepository respuestaConsultaRepository;

    public RespuestaConsultaService(RespuestaConsultaRepository respuestaConsultaRepository) {
        this.respuestaConsultaRepository = respuestaConsultaRepository;
    }

    public List<RespuestaConsulta> findAllResCons() throws SQLException{
        return respuestaConsultaRepository.findAll();
    }

    public int saveResCons(RespuestaConsulta respuestaConsulta) throws SQLException {
        if (respuestaConsulta.getRespuesta() == null || respuestaConsulta.getRespuesta().isEmpty()){
            throw new SQLException("Respuesta vacia");
        }
        String respuesta = respuestaConsulta.getRespuesta();
        if (FiltroLenguaje.contieneGroserias(respuesta)) {
            throw new BadRequestResponse("La biografía contiene lenguaje inapropiado.");
        }
        return respuestaConsultaRepository.save(respuestaConsulta);
    }

    public boolean updateResCons(int id, RespuestaConsulta respuestaConsulta) throws SQLException {
        if (respuestaConsulta.getRespuesta() == null || respuestaConsulta.getRespuesta().isEmpty()){
            throw new SQLException("Respuesta vacia");
        }
        String respuesta = respuestaConsulta.getRespuesta();
        if (FiltroLenguaje.contieneGroserias(respuesta)) {
            throw new BadRequestResponse("La biografía contiene lenguaje inapropiado.");
        }
        return respuestaConsultaRepository.update(id, respuestaConsulta);
    }

    public boolean deleteResCons(int id){
        return respuestaConsultaRepository.delete(id);
    }

    public int contarResCon(int id){
        return respuestaConsultaRepository.contarRespuestasPorAbogado(id);
    }

    public List<RespuestaConsulta> findByUsuarioAbogado(int idUsuarioAbogado) throws SQLException {
        return respuestaConsultaRepository.findByIdUsuarioAbogado(idUsuarioAbogado);
    }

    public List<RespuestaConsulta> findByIdConsulta(int id) throws SQLException {
        return respuestaConsultaRepository.findByIdConsulta(id);
    }
}
