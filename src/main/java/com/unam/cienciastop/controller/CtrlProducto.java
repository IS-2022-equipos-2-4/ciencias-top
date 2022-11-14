package com.unam.cienciastop.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unam.cienciastop.entity.Producto;
import com.unam.cienciastop.service.SvcProducto;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class CtrlProducto {

    @Autowired
    private SvcProducto svcProducto;

    @GetMapping("/productos")
    public ResponseEntity<List<Producto>> getProductos() {
        return new ResponseEntity<>(svcProducto.getProductos(), HttpStatus.OK);
    }

    /**
     * Metodo que recibe un id y regresa el objeto Producto asociado a dicho id.
     * 
     * @param idProducto
     * @return ResponseEntity<Producto>
     */
    @GetMapping("/producto/{id_producto}")
    public ResponseEntity<Producto> getProducto_id(
            @PathVariable(value = "id_producto") Integer idProducto) {
        Producto producto = svcProducto.getProducto_id(idProducto);
        if (producto != null)
            return new ResponseEntity<>(producto, HttpStatus.OK);
        else
            return new ResponseEntity<>(producto, HttpStatus.NO_CONTENT);
    }

    /**
     * Metodo que recibe un codigo y regresa la lista de objetos Producto asociado a dicho codigo.
     * 
     * @param codigo
     * @return ResponseEntity<List<Producto>>
     */
    @GetMapping("/producto/codigo/{codigo}")
    public ResponseEntity<List<Producto>> getProductos_codigo(
            @PathVariable(value = "codigo") String codigo) {
        List<Producto> producto = svcProducto.getProductos_codigo(codigo);
        if (producto != null)
            return new ResponseEntity<>(producto, HttpStatus.OK);
        else
            return new ResponseEntity<>(producto, HttpStatus.NO_CONTENT);
    }

    /**
     * Metodo que recibe un nombre y regresa la lista de objetos Producto asociado a dicho nombre.
     * 
     * @param nombre
     * @return ResponseEntity<List<Producto>>
     */
    @GetMapping("/producto/nombre/{nombre}")
    public ResponseEntity<List<Producto>> getProductos_nombre(
            @PathVariable(value = "nombre") String nombre) {
        List<Producto> producto = svcProducto.getProductos_nombre(nombre);
        if (producto != null)
            return new ResponseEntity<>(producto, HttpStatus.OK);
        else
            return new ResponseEntity<>(producto, HttpStatus.NO_CONTENT);
    }

    @PostMapping("/productos/{id_proveedor}")
    public ResponseEntity<Producto> crearProducto(@Valid @RequestBody Producto producto,
            @PathVariable(value = "id_proveedor") Integer idProveedor) {
        return new ResponseEntity<>(svcProducto.crearProducto(producto, idProveedor),
                HttpStatus.CREATED);
    }
}
