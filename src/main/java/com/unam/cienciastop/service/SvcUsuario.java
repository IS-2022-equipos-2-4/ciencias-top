package com.unam.cienciastop.service;

import java.util.List;

import com.unam.cienciastop.entity.Usuario;

public interface SvcUsuario {
    List<Usuario> getUsuariosActivos();

    public Usuario editarUsuario(EditarUsuarioDto EditarUsuarioDto);
}
