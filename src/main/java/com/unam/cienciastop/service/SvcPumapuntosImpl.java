package com.unam.cienciastop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unam.cienciastop.dao.DaoPumapuntos;
import com.unam.cienciastop.entity.Pumapuntos;

@Service
public class SvcPumapuntosImpl implements SvcPumapuntos{
    @Autowired
    private DaoPumapuntos repoPuma;

    @Override
    public Pumapuntos getPumapuntos(Integer idUsuario) {
        // TODO Auto-generated method stub
        return repoPuma.findById(idUsuario).get();
    }

    @Override
    void sumarPumapuntos(Integer idUsuario, Integer cantidad){
        // TODO implementar
        //repoPuma.findById(idUsuario).
        return; 
    }
}
