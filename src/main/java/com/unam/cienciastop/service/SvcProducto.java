package com.unam.cienciastop.service;

import java.util.List;

import com.unam.cienciastop.entity.Producto;

public interface SvcProducto{
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
}
