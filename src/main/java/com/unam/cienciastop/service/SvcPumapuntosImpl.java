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
    public Integer getPumapuntos(Integer idUsuario) {        
        return repoPuma.findById(idUsuario).get().getSaldo();
    }

    
    @Override
    public Boolean sumarPumapuntos(Integer idUsuario, Integer cantidad){               
        Pumapuntos pumaPuntos = repoPuma.findById(idUsuario).get();
        int saldo = pumaPuntos.getSaldo();
        saldo += cantidad;
        pumaPuntos.setSaldo(saldo);
        repoPuma.save(pumaPuntos);
        return true; 
    }
}
