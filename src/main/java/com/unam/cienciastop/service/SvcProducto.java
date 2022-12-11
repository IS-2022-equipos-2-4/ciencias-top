package com.unam.cienciastop.service;

import java.util.List;

import com.unam.cienciastop.dto.ProductoDTO;
import com.unam.cienciastop.dto.RespuestaDevolverEjemplarDTO;
import com.unam.cienciastop.dto.RespuestaGetEjemplaresDTO;
import com.unam.cienciastop.entity.EjemplarProducto;
import com.unam.cienciastop.entity.HistorialRentas;
import com.unam.cienciastop.entity.Producto;

public interface SvcProducto {
    List<Producto> getProductos();

    /*
     * Metodo que recibe un id y regresa el objeto Producto
     * asociado a dicho id.
     */
    Producto getProducto_id(int id);

    /*
     * MMetodo que recibe un nombre y regresa la lista de objetos 
     * Producto asociado a dicho nombre.
     */
    public List<Producto> getProductos_codigo(String codigo);

    /**
     * Metodo que recibe un nombre y regresa la lista de objetos 
     * Producto asociado a dicho nombre.
     * @param nombre
     * @return List<Producto>
     */
    public List<Producto> getProductos_nombre(String nombre);
    Producto crearProducto(Producto producto, Integer idProveedor);


    public EjemplarProducto rentarProducto(Integer idProducto, String numInstitucionalUsuario);


    public Producto editarProducto(Integer id_producto, ProductoDTO productodto);
    
    public RespuestaDevolverEjemplarDTO devolverEjemplar(Integer idEjemplar);

    /**
     * Metodo que recibe un idEjemplar y regresa la lista de objetos 
     * HistorialRentas asociado a dicho idEjemplar.
     * 
     * @param idEjemplar
     * @return List<HistorialRentas>
     */
    public List<HistorialRentas> verProdRentados(String numInstitucionalUsuario);

    public List<RespuestaGetEjemplaresDTO> getEjemplares(Integer idProducto);

    public List<Producto> getProductosMenorCosto();
}
