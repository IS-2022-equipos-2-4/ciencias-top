package com.unam.cienciastop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unam.cienciastop.dao.DaoUsuario;
import com.unam.cienciastop.dao.DaoProducto;
import com.unam.cienciastop.entity.EjemplarProducto;
import com.unam.cienciastop.entity.Producto;
import com.unam.cienciastop.entity.Usuario;

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

    /*
     * Metodo que recibe un id y regresa el objeto Producto
     * asociado a dicho id.
     */
    @Override
    public Producto getProducto_id(int id){
        return repoProducto.getProducto_id(id);
    }

    /*
     * Metodo que recibe un nombre y regresa la lista de objetos 
     * Producto asociado a dicho nombre.
     */
    @Override
    public List<Producto> getProductos_codigo(String codigo){
        return repoProducto.getProductos_codigo(codigo);
    }

    /**
     * Metodo que recibe un nombre y regresa la lista de objetos 
     * Producto asociado a dicho nombre.
     * @param nombre
     * @return List<Producto>
     */
    @Override
    public List<Producto> getProductos_nombre(String nombre){
        return repoProducto.getProductos_nombre(nombre);
    }

    
    @Override
    public Producto crearProducto(Producto producto, Integer idProveedor) {
        Usuario proveedor = (Usuario) repoUsuario.findById(idProveedor).get();
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