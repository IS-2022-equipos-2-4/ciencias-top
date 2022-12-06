package com.unam.cienciastop.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.annotation.Secured;


import com.unam.cienciastop.dto.UsuarioDTO;
import com.unam.cienciastop.entity.Usuario;
import com.unam.cienciastop.exceptionHandler.ApiException;
import com.unam.cienciastop.service.SvcUsuario;
import com.unam.cienciastop.service.SvcUsuarioImpl;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class CtrlUsuario {

    @Autowired
    private SvcUsuario svcUsuario;

    @Secured("ROLE_ADMIN")
    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> getUsuariosActivos() {
        return new ResponseEntity<>(svcUsuario.getUsuariosActivos(), HttpStatus.OK);
    }

    /**
     * Metodo que recibe un nombre y regresa la lista de objetos Usuario asociado a dicho nombre.
     * 
     * @param nombre
     * @return ResponseEntity<List<Usuario>>
     */
    @Secured("ROLE_ADMIN")
    @GetMapping("/usuarios/nombre/{nombre}")
    public ResponseEntity<List<Usuario>> getUsuarios_nombre(
            @PathVariable(value = "nombre") String nombre) {
        List<Usuario> usuario = svcUsuario.getUsuarios_nombre(nombre);
        if (usuario != null)
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        else
            throw new ApiException(HttpStatus.NOT_FOUND,
                    "ocurrio un error, no se econtraron usuarios");
    }

    /**
     * Metodo que recibe un numero institucional de usuario y regresa la lista de objetos Usuario
     * asociados a dicho numero ingresado.
     * 
     * @param num_institucional
     * @return ResponseEntity<List<Usuario>>
     */
    @Secured("ROLE_ADMIN")
    @GetMapping("/usuarios/institucional/{num_institucional}")
    public ResponseEntity<List<Usuario>> getUsuarios_numeroInstitucional(
            @PathVariable(value = "num_institucional") String num_institucional) {
        List<Usuario> usuario = svcUsuario.getUsuarios_numeroInstitucional(num_institucional);
        if (usuario != null)
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        else
            throw new ApiException(HttpStatus.NOT_FOUND,
                    "ocurrio un error, no se econtraron usuarios");
    }

    /**
     * Metodo que recibe un correo y regresa la lista de objetos Usuario asociados a dicho correo
     * ingresado.
     * 
     * @param correo
     * @return ResponseEntity<List<Usuario>>
     */
    @Secured("ROLE_ADMIN")
    @GetMapping("/usuarios/correo/{correo}")
    public ResponseEntity<List<Usuario>> getUsuarios_correo(
            @PathVariable(value = "correo") String correo) {
        List<Usuario> usuario = svcUsuario.getUsuarios_correo(correo);
        if (usuario != null)
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        else
            throw new ApiException(HttpStatus.NOT_FOUND,
                    "ocurrio un error, no se econtraron usuarios");
    }
    
    @Secured("ROLE_ADMIN")
    @GetMapping("/usuarios/{id_usuario}")
    public ResponseEntity<Usuario> buscarUsuario(
            @PathVariable(value = "id_usuario") Integer idUsuario) {
        return new ResponseEntity<>(svcUsuario.getUsuario_id(idUsuario), HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/usuarios")
    public ResponseEntity<Usuario> crearUsuario(@Valid @RequestBody Usuario usuario,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ApiException(HttpStatus.BAD_REQUEST,
                    bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        return new ResponseEntity<>(svcUsuario.crearUsuario(usuario), HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/usuarios/{id_usuario}")
    public ResponseEntity<Usuario> editarUsuario(
            @PathVariable(value = "id_usuario") Integer id_usuario,
            @Valid @RequestBody UsuarioDTO usuarioDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ApiException(HttpStatus.BAD_REQUEST,
                    bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        return new ResponseEntity<Usuario>(svcUsuario.editarUsuario(id_usuario, usuarioDTO),HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/usuarios/eliminar/{id_usuario}")
    public ResponseEntity<Usuario> eliminarUsuario(
            @PathVariable(value = "id_usuario") Integer id_usuario) {
        return new ResponseEntity<Usuario>(svcUsuario.deleteUsuario(id_usuario), HttpStatus.OK);
    }
}
