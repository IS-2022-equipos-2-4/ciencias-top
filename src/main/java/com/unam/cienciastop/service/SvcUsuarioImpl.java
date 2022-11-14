package com.unam.cienciastop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unam.cienciastop.dao.DaoUsuario;
import com.unam.cienciastop.entity.Usuario;

@Service
public class SvcUsuarioImpl implements SvcUsuario{

    @Autowired
    private DaoUsuario repoUsuario;


    @Override
    public List<Usuario> getUsuariosActivos() {
        return repoUsuario.getUsuariosActivos();
    }

    /*
     * Metodo que recibe un nombre y regresa la lista de objetos 
     * Usuario asociado a dicho nombre.
     */
    @Override
    public List<Usuario> getUsuarios_nombre(String nombre){
        return repoUsuario.getUsuarios_nombre(nombre);
    }

    /*
     * Metodo que recibe un numero institucional y regresa la lista de objetos Usuario
     * asociados a dicho numero.
     */
    @Override
    public List<Usuario> getUsuarios_numeroInstitucional(String num_institucional){
        return repoUsuario.getUsuarios_numeroInstitucional(num_institucional);
    }

    /*
     * Metodo que recibe correo y regresa la lista de objetos Usuario
     * asociados a dicho correo.
     */
    @Override
    public List<Usuario> getUsuarios_correo(String correo){
        return repoUsuario.getUsuarios_correo(correo);
    }

    @Override
    public Usuario crearUsuario(Usuario usuario){
        
        return repoUsuario.save(usuario);
    }
}