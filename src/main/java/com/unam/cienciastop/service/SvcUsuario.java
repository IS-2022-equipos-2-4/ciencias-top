package com.unam.cienciastop.service;

import java.util.List;

import com.unam.cienciastop.dto.UsuarioDTO;
import org.springframework.http.ResponseEntity;

import com.unam.cienciastop.entity.Usuario;

public interface SvcUsuario {
    List<Usuario> getUsuariosActivos();

    /*
     * Metodo que recibe un nombre y regresa la lista de objetos 
     * Usuario asociado a dicho nombre.
     */
    public List<Usuario> getUsuarios_nombre(String nombre);

    /*
     * Metodo que recibe un numero institucional y regresa la lista de objetos Usuario
     * asociados a dicho numero.
     */
    public List<Usuario> getUsuarios_numeroInstitucional(String num_institucional);
    
    /**
     * Metodo que recibe un correo y regresa la lista de objetos Usuario
     * asociados a dicho correo.
     */
    public List<Usuario> getUsuarios_correo(String correo);

    Usuario crearUsuario(Usuario usuario);

    public Usuario editarUsuario(UsuarioDTO usuarioDTO);
}
