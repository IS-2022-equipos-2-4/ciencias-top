package com.unam.cienciastop.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.unam.cienciastop.entity.Producto;
import com.unam.cienciastop.entity.EjemplarProducto;
import com.unam.cienciastop.entity.HistorialRentas;
import com.unam.cienciastop.entity.Usuario;
import com.unam.cienciastop.dto.ProductoDTO;
import com.unam.cienciastop.dto.RespuestaDevolverEjemplarDTO;
import com.unam.cienciastop.dto.RespuestaGetEjemplaresDTO;
import com.unam.cienciastop.exceptionHandler.ApiException;
import com.unam.cienciastop.service.SvcProducto;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class CtrlProducto {

    @Autowired
    private SvcProducto svcProducto;

    @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_PROVIDER"})
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

    @Secured({"ROLE_ADMIN", "ROLE_PROVIDER"})
    @GetMapping("/productos/{id_producto}")
    public ResponseEntity<Producto> getProducto_id(
            @PathVariable(value = "id_producto") Integer idProducto) {
        Producto producto = svcProducto.getProducto_id(idProducto);
        if (producto != null)
            return new ResponseEntity<>(producto, HttpStatus.OK);
        else
            throw new ApiException(HttpStatus.NOT_FOUND, "no existe un producto con ese id");
    }

    /**
     * Metodo que recibe un codigo y regresa la lista de objetos Producto asociado a dicho codigo.
     * 
     * @param codigo
     * @return ResponseEntity<List<Producto>>
     */
    @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_PROVIDER"})
    @GetMapping("/productos/codigo/{codigo}")
    public ResponseEntity<List<Producto>> getProductos_codigo(
            @PathVariable(value = "codigo") String codigo) {
        List<Producto> producto = svcProducto.getProductos_codigo(codigo);
        if (producto != null)
            return new ResponseEntity<>(producto, HttpStatus.OK);
        else
            throw new ApiException(HttpStatus.NOT_FOUND,
                    "ocurrio un error, no se econtraron productos");
    }

    /**
     * Metodo que recibe un nombre y regresa la lista de objetos Producto asociado a dicho nombre.
     * 
     * @param nombre
     * @return ResponseEntity<List<Producto>>
     */
    @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_PROVIDER"})
    @GetMapping("/productos/nombre/{nombre}")
    public ResponseEntity<List<Producto>> getProductos_nombre(
            @PathVariable(value = "nombre") String nombre) {
        List<Producto> producto = svcProducto.getProductos_nombre(nombre);
        if (producto != null)
            return new ResponseEntity<>(producto, HttpStatus.OK);
        else
            throw new ApiException(HttpStatus.NOT_FOUND,
                    "ocurrio un error, no se econtraron productos");
    }

    @Secured({"ROLE_ADMIN", "ROLE_PROVIDER"})
    @PostMapping("/productos/{id_proveedor}")
    public ResponseEntity<Producto> crearProducto(@Valid @RequestBody Producto producto,
            BindingResult bindingResult,
            @PathVariable(value = "id_proveedor") Integer idProveedor) {

        if (bindingResult.hasErrors()) {
            throw new ApiException(HttpStatus.BAD_REQUEST,
                    bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        return new ResponseEntity<>(svcProducto.crearProducto(producto, idProveedor),
                HttpStatus.CREATED);
    }

    @Secured({"ROLE_ADMIN", "ROLE_PROVIDER"})
    @PostMapping("/productos/editar/{id_producto}")
    public ResponseEntity<Producto> editarProducto(
            @PathVariable(value = "id_producto") Integer id_producto,
            @Valid @RequestBody ProductoDTO productodto,
            BindingResult bindingRes) {
        if (bindingRes.hasErrors()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, 
                    bindingRes.getAllErrors().get(0).getDefaultMessage());
        }
        return new ResponseEntity<Producto>(svcProducto.editarProducto(id_producto, productodto), HttpStatus.OK);
    }


    @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_PROVIDER"})
    @PostMapping("/productos/{id_producto}/rentar")
    public ResponseEntity<EjemplarProducto> rentarProducto(
            @PathVariable(value = "id_producto") Integer idProducto,
            @AuthenticationPrincipal String numInstitucionalUsuario) {
        return new ResponseEntity<EjemplarProducto>(
                svcProducto.rentarProducto(idProducto, numInstitucionalUsuario), HttpStatus.OK);
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping("/productos/ejemplares/{id_ejemplar}/devolver")
    public ResponseEntity<RespuestaDevolverEjemplarDTO> devolverEjemplar(
            @PathVariable(value = "id_ejemplar") Integer idEjemplar) {
        return new ResponseEntity<RespuestaDevolverEjemplarDTO>(
                svcProducto.devolverEjemplar(idEjemplar), HttpStatus.OK);
    }

    /**
     * Metodo que recibe un numInstitucionalUsuario y regresa la lista de objetos HistorialRentas
     * asociado a dicho idEjemplar.
     * 
     * @param numInstitucionalUsuario
     * @return la lista de objetos HistorialRentas del id_usuario que se le pasa por parametro
     */
    @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_PROVIDER"})
    @GetMapping("/productos/productos-rentados")
    public ResponseEntity<List<HistorialRentas>> verProdRentados(
            @AuthenticationPrincipal String numInstitucionalUsuario) {
        List<HistorialRentas> prod_rent = svcProducto.verProdRentados(numInstitucionalUsuario);
        if (prod_rent != null)
            return new ResponseEntity<>(prod_rent, HttpStatus.OK);
        else
            throw new ApiException(HttpStatus.NOT_FOUND,
                    "ocurrio un error, no se econtraron productos");
    }

    /**
     * Método que recibe el id de un producto y devuelve los ejemplares de este
     * 
     * @param idProducto
     * @return RespuestaGetEjemplares
     */
    @Secured({"ROLE_ADMIN"})
    @GetMapping("/productos/{id_producto}/ejemplares")
    public ResponseEntity<List<RespuestaGetEjemplaresDTO>> getEjemplares(
            @PathVariable(value = "id_producto") Integer idProducto) {
        return new ResponseEntity<List<RespuestaGetEjemplaresDTO>>(
                svcProducto.getEjemplares(idProducto), HttpStatus.OK);
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/productos/reporte/menor-costo")
    public ResponseEntity<List<Producto>> getProductosMenorCosto(){
        return new ResponseEntity<>(svcProducto.getProductosMenorCosto(), HttpStatus.OK);
    }
}
