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

import com.unam.cienciastop.entity.Usuario;
import com.unam.cienciastop.service.SvcUsuario;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class CtrlUsuario {

    @Autowired
    private SvcUsuario svcUsuario;

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
    @GetMapping("/usuario/nombre/{nombre}")
    public ResponseEntity<List<Usuario>> getUsuarios_nombre(
            @PathVariable(value = "nombre") String nombre) {
        List<Usuario> usuario = svcUsuario.getUsuarios_nombre(nombre);
        if (usuario != null)
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        else
            return new ResponseEntity<>(usuario, HttpStatus.NO_CONTENT);
    }

    /**
     * Metodo que recibe un numero institucional de usuario y regresa el objeto Usuario
     * asociado a dicho numero ingresado.
     * 
     * @param num_institucional
     * @return ResponseEntity<Usuario>
     */
    @GetMapping("/usuario/{num_institucional}")
    public ResponseEntity<Usuario> getUsuario_numeroInstitucional(
            @PathVariable(value = "num_institucional") String num_institucional) {
        Usuario usuario = svcUsuario.getUsuario_numeroInstitucional(num_institucional);
        if (usuario != null)
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        else
            return new ResponseEntity<>(usuario, HttpStatus.NO_CONTENT);
    }

    /**
     * Metodo que recibe un correo y regresa el objeto Usuario asociado a correo ingresado.
     * 
     * @param correo
     * @return ResponseEntity<Usuario>
     */
    @GetMapping("/usuario/{correo}")
    public ResponseEntity<Usuario> getUsuario_correo(
            @PathVariable(value = "correo") String correo) {
        Usuario usuario = svcUsuario.getUsuario_correo(correo);
        if (usuario != null)
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        else
            return new ResponseEntity<>(usuario, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/usuarios/{id_usuario}")
    public ResponseEntity<Usuario> buscarUsuario(
            @PathVariable(value = "id_usuario") Integer idUsuario) {
        return null;
    }

    @PostMapping("/usuarios")
    public ResponseEntity<Usuario> crearUsuario(@Valid @RequestBody Usuario Usuario) {
        return null;
    }

    @PostMapping("/usuarios/{id_usuario}")
    public ResponseEntity<Usuario> editarUsuario(
            @PathVariable(value = "id_usuario") Integer idUsuario,
            @Valid @RequestBody Usuario usuario) {
        return null;
    }
}