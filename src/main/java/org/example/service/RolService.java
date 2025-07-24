package org.example.service;

import org.example.model.Rol;
import org.example.repository.RolRepository;

import java.sql.SQLException;
import java.util.List;
/*Aqui se cargan tal cual los datos y tabmien puedes hacer validaciones aunque si haces una validacion aqui tambien tendras que implementarlo
* en el apartado de controladores pero permite reutilizar validaciones para las capas que le siguen*/
public class RolService {
    private final RolRepository rolRepo;

    public RolService(RolRepository rolRepo) {
        this.rolRepo = rolRepo;
    }

    public List<Rol> getAll() throws SQLException { return rolRepo.findAll(); }

    public int saveRol(Rol rol) {
        return rolRepo.save(rol);
    }

    public boolean updateRol(int id_rol, Rol rol) { return rolRepo.update(id_rol, rol); }

    public boolean deleteRol(int id_rol){ return rolRepo.delete(id_rol); }

}