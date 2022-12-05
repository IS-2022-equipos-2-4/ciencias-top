package com.unam.cienciastop.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.unam.cienciastop.dto.ProductosDeLaSemanaDTO;
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
     * Metodo que despliega los productos mas rentados de la semana
     * @return List<ProductosDeLaSemanaDTO>
     */
    @Query(value = "SELECT nombre, descripcion, COUNT(nombre) AS unidades_vendidas FROM historial_rentas LEFT JOIN ejemplar_productos ON historial_rentas.id_ejemplar = ejemplar_productos.id_ejemplar LEFT JOIN productos ON ejemplar_productos.id_producto = productos.id_producto WHERE EXTRACT(DAY FROM NOW() - fecha_renta) < 7 GROUP BY nombre, descripcion ORDER BY unidades_vendidas DESC fetch first 5 rows only", nativeQuery = true)
    public List<ProductosDeLaSemanaDTO> getProductosDeLaSemana();
    
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
