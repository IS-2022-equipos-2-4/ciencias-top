package com.unam.cienciastop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unam.cienciastop.dao.DaoUsuario;
import com.unam.cienciastop.dao.DaoProducto;
import com.unam.cienciastop.entity.EjemplarProducto;
import com.unam.cienciastop.entity.Producto;

@Service
public class SvcProductoImpl implements SvcProducto {
    
    @Autowired
    private DaoUsuario repoUsuario;

    @Autowired
    private DaoProducto repoProducto;

    @Autowired
    private SvcEjemplarProducto svcEProducto;

    @Autowired
    private SvcUsuario svcUsuario;

    @Override
    public List<Producto> getProductos() {
        // TODO Auto-generated method stub
        return (List<Producto>) repoProducto.findAll();
    }

    @Override
    public Producto crearProducto(Producto producto, Integer idProveedor) {
        Usuario proveedor=(Usuario) repoUsuario.findbyid(idProveedor);
        producto.setProveedor(proveedor);
        Producto nuevo = (Producto) repoProducto.save(producto);
        Integer unidades = producto.getStock();
        for (int i = 0; i < unidades; i++) {
            EjemplarProducto e = new EjemplarProducto(true, nuevo);
            svcEProducto.crearEjemplar(e);
        }
        return nuevo;
    }
}