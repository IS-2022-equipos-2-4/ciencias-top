package com.unam.cienciastop.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.unam.cienciastop.entity.Producto;
import com.unam.cienciastop.entity.Usuario;

public interface DaoProducto extends CrudRepository<Producto,Integer>{
    /**
     * Metodo que recibe un id y regresa el objeto Producto
     * asociado a dicho id.
     * @param id_producto
     * @return Producto
     */
    @Query(value = "SELECT * FROM productos WHERE id_producto = :id_producto", nativeQuery = true)
    public Producto getProducto_id(@Param("id_producto") Integer id_producto);
    
    /** 
     * Metodo que recibe un nombre y regresa la lista de objetos 
     * Producto asociado a dicho nombre.
     * @param codigo
     * @return List<Producto>
     */
    @Query(value = "SELECT * FROM productos WHERE POSITION (:codigo IN codigo)>0", nativeQuery = true)
    public List<Producto> getProductos_codigo(@Param("codigo") String codigo);

    /**
     * Metodo que recibe un nombre y regresa la lista de objetos 
     * Producto asociado a dicho nombre.
     * @param nombre
     * @return List<Producto>
     */
    @Query(value = "SELECT * FROM productos WHERE POSITION (:nombre IN nombre)>0", nativeQuery = true)
    public List<Producto> getProductos_nombre(@Param("nombre") String nombre);

    List<Producto> findByProveedor(Usuario proveedor);
}
