package com.unam.cienciastop.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.unam.cienciastop.entity.Usuario;

public interface DaoUsuario extends CrudRepository<Usuario,Integer>{

    @Query(value = "SELECT * FROM usuarios WHERE activo = true", nativeQuery = true)
    public List<Usuario> getUsuariosActivos();


    /**
     * Metodo que regresa los usuarios inactivos de la base de datos
     * @return
     */
    @Query(value = "SELECT * FROM usuarios WHERE activo = false", nativeQuery = true)
    public List<Usuario> getUsuariosInactivos();

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

    /**
     * Metodo que recibe un correo y regresa la lista de objetos Usuario
     * asociados a dicho correo.
     * @param correo
     * @return List<Usuario>
     */
    @Query(value = "SELECT * FROM usuarios WHERE POSITION (:correo IN correo)>0", nativeQuery = true)
    public List<Usuario> getUsuarios_correo(@Param("correo") String correo);

    @Query(value = "SELECT b.*,COUNT(*)" +
                    "FROM historial_rentas a LEFT JOIN usuarios b ON a.id_usuario = b.id_usuario " +
                    "WHERE a.devuelto = true" +
                    "GROUP BY b.id_usuario ORDER BY count DESC LIMIT 10;", 
                nativeQuery=true)
    public List<Usuario> getUsuariosConMasDevoluciones();

    Usuario findByNumInstitucional(String numInstitucional);
}
