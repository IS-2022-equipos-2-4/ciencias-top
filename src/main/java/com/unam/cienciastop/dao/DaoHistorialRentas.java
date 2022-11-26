package com.unam.cienciastop.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.unam.cienciastop.entity.HistorialRentas;

public interface DaoHistorialRentas extends CrudRepository<HistorialRentas,Integer>{
     /**
     * Obtiene los pumapuntos de un usuario a través de su id
     * @param id_usuario
     * @return Pumapuntos
     */
    @Query(value = "SELECT * FROM historial_rentas WHERE id_usuario = :id_usuario", nativeQuery = true)
    public List<HistorialRentas> rentasByIdUsuario(@Param("id_usuario") Integer id_usuario);
}
