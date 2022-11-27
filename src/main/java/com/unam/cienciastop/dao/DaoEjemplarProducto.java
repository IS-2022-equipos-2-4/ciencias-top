package com.unam.cienciastop.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.unam.cienciastop.entity.EjemplarProducto;

public interface DaoEjemplarProducto extends CrudRepository<EjemplarProducto, Integer> {

    /**
     * Metodo que recibe el id de un producto y devuelve todos los ejemplares disponibles de este. 
     * 
     * @param id_producto
     * @return List<EjemplarProducto>
     */
    @Query(value = "SELECT * FROM ejemplar_productos WHERE id_producto = :id_producto AND disponible = true",
            nativeQuery = true)
    public List<EjemplarProducto> getEjemplaresDisponiblesByIdProducto(
            @Param("id_producto") Integer id_producto);
}
