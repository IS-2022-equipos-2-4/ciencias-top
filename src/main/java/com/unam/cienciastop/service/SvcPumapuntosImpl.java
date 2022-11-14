package com.unam.cienciastop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.unam.cienciastop.dao.DaoPumapuntos;
import com.unam.cienciastop.entity.Pumapuntos;
import com.unam.cienciastop.exceptionHandler.ApiException;

@Service
public class SvcPumapuntosImpl implements SvcPumapuntos{
    @Autowired
    private DaoPumapuntos repoPuma;

    @Override
    public Pumapuntos getPumapuntos(Integer idUsuario) {
        try {
            return repoPuma.findById(idUsuario).get();
        } catch (DataAccessException e){
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "error en la consulta a la base de datos");
        } catch (Exception e) {
            throw new ApiException(HttpStatus.NOT_FOUND, e.getLocalizedMessage());
        }
    }
}
