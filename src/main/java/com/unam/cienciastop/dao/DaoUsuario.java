package com.unam.cienciastop.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.unam.cienciastop.entity.Usuario;

public interface DaoUsuario extends CrudRepository<Usuario,Integer>{
    
    @Query(value = "SELECT * FROM usuarios WHERE activo = true", nativeQuery = true)
    public List<Usuario> getUsuariosActivos();

    public void editarUsuario(Integer id, String nombre, String telefono)
}
