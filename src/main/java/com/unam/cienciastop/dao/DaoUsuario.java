package com.unam.cienciastop.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.unam.cienciastop.entity.Usuario;

public interface DaoUsuario extends CrudRepository<Usuario,Integer>{

    @Query(value = "SELECT * FROM usuarios WHERE activo = true", nativeQuery = true)
    public List<Usuario> getUsuariosActivos();

    /**
     * Metodo que recibe un nombre y regresa la lista de objetos
     * Usuario asociado a dicho nombre.
     * @param nombre
     * @return List<Usuario>
     */
    @Query(value = "SELECT * FROM usuarios WHERE POSITION (:nombre IN nombre)>0", nativeQuery = true)
    public List<Usuario> getUsuarios_nombre(@Param("nombre") String nombre);

    /**
     * Metodo que recibe un numero institucional y regresa la lista de objetos Usuario
     * asociados a dicho numero.
     * @param num_institucional
     * @return List<Usuario>
     */
    @Query(value = "SELECT * FROM usuarios WHERE POSITION (:num_institucional IN num_institucional)>0", nativeQuery = true)
    public List<Usuario> getUsuarios_numeroInstitucional(@Param("num_institucional") String num_institucional);

//     /**
//      * Metodo que recibe un correo y regresa la lista de objetos Usuario
//      * asociados a dicho correo.
//      * @param correo
//      * @return List<Usuario>
//      */
//     @Query(value = "SELECT * FROM usuarios WHERE POSITION (:correo IN correo)>0", nativeQuery = true)
//     public List<Usuario> getUsuarios_correo(@Param("correo") String correo);

//     @Modifying
//     @Query(value = "UPDATE usuarios SET nombre = new_nombre, correo = new_correo," +
//            "telefono = new_telefono, esProveedor = new_esProveedor, esAdmin = new_esAdmin WHERE usuario.id = id;")
//     public void editarUsuario(Integer id, String new_nombre, String new_correo, String new_telefono,
//                               Boolean new_esProveedor, Boolean new_esAdmin);
}
