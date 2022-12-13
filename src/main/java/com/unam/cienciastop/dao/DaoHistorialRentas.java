package com.unam.cienciastop.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.unam.cienciastop.entity.HistorialRentas;

public interface DaoHistorialRentas extends CrudRepository<HistorialRentas,Integer>{
     /**
     * Obtiene una lista de objetos HistorialRentas asociado al 
     * parametro id_usuario que se le pasa por parametro.
     * @param id_usuario
     * @return List<HistorialRentas>
     */
    @Query(value = "SELECT * FROM historial_rentas WHERE id_usuario = :id_usuario", nativeQuery = true)
    public List<HistorialRentas> rentasByIdUsuario(@Param("id_usuario") Integer id_usuario);

    /**
     * Obtiene una lista de objetos HistorialRentas asociado al 
     * parametro id_ejemplar que se le pasa por parametro.
     * @param id_ejemplar
     * @return List<HistorialRentas>
     */
    @Query(value = "SELECT * FROM historial_rentas WHERE id_ejemplar = :id_ejemplar", nativeQuery = true)
    public List<HistorialRentas> rentasByIdEjemplar(@Param("id_ejemplar") Integer id_ejemplar);
}
