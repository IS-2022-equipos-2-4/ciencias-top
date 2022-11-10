package com.unam.cienciastop.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unam.cienciastop.entity.Producto;
import com.unam.cienciastop.service.SvcProducto;

@RestController
@RequestMapping("/api")
public class CtrlProducto {

    @Autowired
    private SvcProducto svcProducto;

    @GetMapping("/productos")
    public ResponseEntity<List<Producto>> getProductos() {
        return new ResponseEntity<>(svcProducto.getProductos(), HttpStatus.OK);
    }

    @GetMapping("/productos/{id_producto}")
    public ResponseEntity<Producto> buscarProducto(@PathVariable(value = "id_producto") Integer idProducto) {
        return null;
    }

    @PostMapping("/productos/{id_proveedor}")
    public ResponseEntity<Producto> crearProducto(@Valid @RequestBody Producto producto, @PathVariable(value = "id_proveedor") Integer idProveedor) {
        return new ResponseEntity<>(svcProducto.crearProducto(producto,idProveedor), HttpStatus.CREATED);
    }
}
