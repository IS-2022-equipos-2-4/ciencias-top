package com.unam.cienciastop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unam.cienciastop.dao.DaoPumapuntos;
import com.unam.cienciastop.entity.Pumapuntos;
import com.unam.cienciastop.exceptionHandler.ApiException;

@Service
public class SvcPumapuntosImpl implements SvcPumapuntos{
    @Autowired
    private DaoPumapuntos repoPuma;

    @Override
    @Transactional(readOnly = true)
    public Integer getPumapuntos(Integer idUsuario) {
        try {
            return repoPuma.findById(idUsuario).get().getSaldo();
        } catch (DataAccessException e){
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "error en la consulta a la base de datos");
        } catch (Exception e) {
            throw new ApiException(HttpStatus.NOT_FOUND, e.getLocalizedMessage());
        }
    }

    
    @Override
    @Transactional
    public Integer sumarPumapuntos(Integer idUsuario, Integer cantidad){               
        try{
            Pumapuntos pumaPuntos = repoPuma.findById(idUsuario).get();
            int saldo = pumaPuntos.getSaldo();
            saldo += cantidad;

            if (saldo < 0)
                throw new ApiException(HttpStatus.NOT_FOUND, "La cantidad a restar supera el saldo actual");

            pumaPuntos.setSaldo(saldo);
            repoPuma.save(pumaPuntos);
            return saldo; 
        } catch (DataAccessException e){
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "error en la consulta a la base de datos");
        } catch (Exception e) {
            throw new ApiException(HttpStatus.NOT_FOUND, e.getLocalizedMessage());
        }
    }
}
