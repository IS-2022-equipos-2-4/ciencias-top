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
