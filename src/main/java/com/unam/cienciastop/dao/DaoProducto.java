package com.unam.cienciastop.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.unam.cienciastop.entity.Producto;

public interface DaoProducto extends CrudRepository<Producto,Integer>{
    /**
     * @param id_producto
     * @return El objeto Producto asociado a dicho id.
     */
    @Query(value = "SELECT * FROM productos WHERE id_producto = :id_producto", nativeQuery = true)
    public Producto getProductById(@Param("id_producto") Integer id_producto);
}
