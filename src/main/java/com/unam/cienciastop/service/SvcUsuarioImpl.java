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
        // TODO Auto-generated method stub
        return repoUsuario.getUsuariosActivos();
    }
}