package com.unam.cienciastop.dao;

import java.util.List;

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
     * Metodo que recibe un numero institucional y regresa el objeto Usuario
     * asociado a dicho numero.
     * @param num_institucional
     * @return Usuario
     */
    @Query(value = "SELECT * FROM usuarios WHERE num_institucional = :num_institucional", nativeQuery = true)
    public Usuario getUsuario_numeroInstitucional(@Param("num_institucional") String num_institucional);

    /**
     * Metodo que recibe un correo y regresa el objeto Usuario
     * asociado a dicho correo.
     * @param correo
     * @return Usuario
     */
    @Query(value = "SELECT * FROM usuarios WHERE correo = :correo", nativeQuery = true)
    public Usuario getUsuario_correo(@Param("correo") String correo);

}
