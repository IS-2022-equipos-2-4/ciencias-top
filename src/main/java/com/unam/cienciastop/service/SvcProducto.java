package com.unam.cienciastop.service;

import java.util.List;

import com.unam.cienciastop.entity.Producto;

public interface SvcProducto{
    List<Producto> getProductos();

    /*
     * Metodo que recibe un id y regresa el objeto Producto
     * asociado a dicho id.
     */
    Producto getProductById(int id);
}
