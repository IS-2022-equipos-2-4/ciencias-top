package com.unam.cienciastop.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.unam.cienciastop.entity.Usuario;

public interface SvcUsuario {
    List<Usuario> getUsuariosActivos();

    Usuario crearUsuario(Usuario usuario);

}
