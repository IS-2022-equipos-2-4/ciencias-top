package com.unam.cienciastop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.unam.cienciastop.dao.DaoEjemplarProducto;
import com.unam.cienciastop.entity.EjemplarProducto;
import com.unam.cienciastop.exceptionHandler.ApiException;

@Service
public class SvcEjemplarProductoImpl implements SvcEjemplarProducto {

    @Autowired
    private DaoEjemplarProducto repoEProducto;

    @Override
    public void crearEjemplar(EjemplarProducto item) {
        try {
            repoEProducto.save(item);    
        } catch (DataAccessException e){
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "error en la consulta a la base de datos");
        } catch (Exception e) {
            throw new ApiException(HttpStatus.NOT_FOUND, e.getLocalizedMessage());
        }
        
    }
}
