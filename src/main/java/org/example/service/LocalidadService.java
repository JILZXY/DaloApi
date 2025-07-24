package org.example.service;

import org.example.model.Localidad;
import org.example.repository.LocalidadRepository;

import java.sql.SQLException;
import java.util.List;

public class LocalidadService {

    private boolean validarLocalidad(Localidad localidad) {
        if (localidad == null) return false;
        if (localidad.getEstado() == null || localidad.getEstado().trim().isEmpty()) return false;
        if (localidad.getMunicipio() == null || localidad.getMunicipio().trim().isEmpty()) return false;
        return true;
    }


    private final LocalidadRepository localidadRepo;

    public LocalidadService(LocalidadRepository localidadRepo){this.localidadRepo = localidadRepo;}

    public List<Localidad> findAllLocalidad() throws SQLException{ return localidadRepo.findAll();}

    public int saveLocalidad(Localidad localidad) {
        if (!validarLocalidad(localidad)) {
            throw new IllegalArgumentException("Los campos 'estado' y 'municipio' no pueden estar vacíos");
        }
        return localidadRepo.save(localidad);
    }

    public boolean updateLocalidad(int id_localidad, Localidad localidad){
        if (!validarLocalidad(localidad)) {
            throw new IllegalArgumentException("Los campos 'estado' y 'municipio' no pueden estar vacíos");
        }
        return localidadRepo.update(id_localidad, localidad);
    }

    public boolean deleteLocalidad(int id_localidad){ return localidadRepo.delete(id_localidad);}

    public Localidad obtenerPorId(int id) throws SQLException {
        return localidadRepo.findById(id);
    }

}
