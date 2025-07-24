package org.example.service;

import io.javalin.http.BadRequestResponse;
import io.javalin.http.NotFoundResponse;
import org.example.model.Abogado;
import org.example.repository.AbogadoRepository;

import java.sql.SQLException;
import java.util.List;

public class AbogadoService {
    private final AbogadoRepository abogadoRepository;

    public AbogadoService(AbogadoRepository abogadoRepository) {
        this.abogadoRepository = abogadoRepository;
    }

    public List<Abogado> findAllAbogado() throws SQLException{
        return abogadoRepository.findAll();
    }

    public int saveAbogado(Abogado abogado) throws SQLException {
        if (abogado.getBiografia() == null || abogado.getBiografia().isEmpty()){
            throw new SQLException("La biografia esta vacia");
        }
        if (abogado.getCedulaProfesional() == null || abogado.getCedulaProfesional().isEmpty()){
            throw new SQLException("La cedula profesional esta vacia");
        }

        String cedulaProfesional = abogado.getCedulaProfesional();
        if (!cedulaProfesional.matches("\\d{7,8}")) {
            throw new BadRequestResponse("La cédula profesional no tiene un formato válido. Debe contener solo números y tener una longitud específica.");
        }

        if (abogado.getDescripcion() == null || abogado.getDescripcion().isEmpty()){
            throw new SQLException("La descripcion esta vacia");
        }
        String biografia = abogado.getBiografia();
        String descripcion = abogado.getDescripcion();
        if (FiltroLenguaje.contieneGroserias(biografia)) {
            throw new BadRequestResponse("La biografía contiene lenguaje inapropiado.");
        }
        if (FiltroLenguaje.contieneGroserias(descripcion)){
            throw new BadRequestResponse("La descripcion contiene lenguaje inapropiado");
        }
        return abogadoRepository.save(abogado);
    }

    public boolean updateAbogado(int id, Abogado abogado) throws SQLException {
        if (abogado.getBiografia() == null || abogado.getBiografia().isEmpty()){
            throw new NotFoundResponse("La biografia esta vacia");
        }
        if (abogado.getCedulaProfesional() == null || abogado.getCedulaProfesional().isEmpty()){
            throw new NotFoundResponse("La cedula profesional esta vacia");
        }

        String cedulaProfesional = abogado.getCedulaProfesional();
        if (!cedulaProfesional.matches("\\d{7,8}")) {
            throw new BadRequestResponse("La cédula profesional no tiene un formato válido. Debe contener solo números y tener una longitud específica.");
        }

        if (abogado.getDescripcion() == null || abogado.getDescripcion().isEmpty()){
            throw new NotFoundResponse("La descripcion esta vacia");
        }
        String biografia = abogado.getBiografia();
        String descripcion = abogado.getDescripcion();
        if (FiltroLenguaje.contieneGroserias(biografia)) {
            throw new BadRequestResponse("La biografía contiene lenguaje inapropiado.");
        }
        if (FiltroLenguaje.contieneGroserias(descripcion)){
            throw new BadRequestResponse("La descripcion contiene lenguaje inapropiado");
        }
        return abogadoRepository.update(id, abogado);
    }

    public boolean deleteAboagdo(int id) throws SQLException {
        return abogadoRepository.delete(id);
    }
}