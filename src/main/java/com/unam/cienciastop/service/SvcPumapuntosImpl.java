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
    public int getPumapuntos(Integer idUsuario) {
        // TODO Auto-generated method stub
        return repoPuma.findById(idUsuario).get().getSaldo();
    }

    
    @Override
    public void sumarPumapuntos(Integer idUsuario, Integer cantidad){
        // TODO implementar
        int saldo = repoPuma.findById(idUsuario).get().getSaldo();
        saldo = saldo + cantidad;
        repoPuma.findById(idUsuario).get().setSaldo(saldo);
        return; 
    }
}
