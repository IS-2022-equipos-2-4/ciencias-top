package com.unam.cienciastop.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.unam.cienciastop.entity.Usuario;

public interface DaoUsuario extends CrudRepository<Usuario,Integer>{
    
    @Query(value = "SELECT * FROM usuarios WHERE activo = true", nativeQuery = true)
    public List<Usuario> getUsuariosActivos();

    @Modifying
    @Query(value = "UPDATE usuarios SET nombre = new_nombre, correo = new_correo," + 
     "telefono = new_telefono, esProveedor = new_esProveedor, esAdmin = new_esAdmin WHERE usuario.id = id;")
    public void editarUsuario(Integer id, String new_nombre, String new_correo, 
                String new_telefono, Boolean new_esProveedor, Boolean new_esAdmin);
}
