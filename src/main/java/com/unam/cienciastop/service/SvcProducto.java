package com.unam.cienciastop.service;

import java.util.List;

import com.unam.cienciastop.entity.Producto;

public interface SvcProducto{
    List<Producto> getProductos();

    void crearProducto(Producto producto, Integer idProveedor);
}
