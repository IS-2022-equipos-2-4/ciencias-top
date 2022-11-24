package com.unam.cienciastop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.unam.cienciastop.dao.DaoUsuario;
import com.unam.cienciastop.dao.DaoProducto;
import com.unam.cienciastop.entity.EjemplarProducto;
import com.unam.cienciastop.entity.Producto;
import com.unam.cienciastop.entity.Usuario;
import com.unam.cienciastop.exceptionHandler.ApiException;

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
        try {
            return (List<Producto>) repoProducto.findAll();
        } catch (DataAccessException e){
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "error en la consulta a la base de datos");
        } catch (Exception e) {
            throw new ApiException(HttpStatus.NOT_FOUND, e.getLocalizedMessage());
        }
    }

    /*
     * Metodo que recibe un id y regresa el objeto Producto
     * asociado a dicho id.
     */
    @Override
    public Producto getProducto_id(int id){
        try {
            return repoProducto.getProducto_id(id);    
        } catch (DataAccessException e){
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "error en la consulta a la base de datos");
        } catch (Exception e) {
            throw new ApiException(HttpStatus.NOT_FOUND, e.getLocalizedMessage());
        }
    }

    /*
     * Metodo que recibe un nombre y regresa la lista de objetos 
     * Producto asociado a dicho nombre.
     */
    @Override
    public List<Producto> getProductos_codigo(String codigo){
        try {
            return repoProducto.getProductos_codigo(codigo);    
        } catch (DataAccessException e){
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "error en la consulta a la base de datos");
        } catch (Exception e) {
            throw new ApiException(HttpStatus.NOT_FOUND, e.getLocalizedMessage());
        }
    }

    /**
     * Metodo que recibe un nombre y regresa la lista de objetos 
     * Producto asociado a dicho nombre.
     * @param nombre
     * @return List<Producto>
     */
    @Override
    public List<Producto> getProductos_nombre(String nombre){
        try {
            return repoProducto.getProductos_nombre(nombre);
        } catch (DataAccessException e){
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "error en la consulta a la base de datos");
        } catch (Exception e) {
            throw new ApiException(HttpStatus.NOT_FOUND, e.getLocalizedMessage());
        }
    }

    
    @Override
    public Producto crearProducto(Producto producto, Integer idProveedor) {
        try {
            Usuario proveedor = (Usuario) repoUsuario.findById(idProveedor).get();
            producto.setProveedor(proveedor);
            Producto nuevo = (Producto) repoProducto.save(producto);
            Integer unidades = producto.getStock();
            for (int i = 0; i < unidades; i++) {
                EjemplarProducto e = new EjemplarProducto(true, nuevo);
                svcEProducto.crearEjemplar(e);
            }
            return nuevo;
        } catch (DataIntegrityViolationException e){
            throw new ApiException(HttpStatus.NOT_FOUND, "ya hay un producto registrado con ese codigo");
        } catch (DataAccessException e){
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "error en la consulta a la base de datos");
        } catch (Exception e) {
            throw new ApiException(HttpStatus.NOT_FOUND, e.getLocalizedMessage());
        }
    }
}