package com.unam.cienciastop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unam.cienciastop.dao.DaoEjemplarProducto;
import com.unam.cienciastop.entity.EjemplarProducto;

@Service
public class SvcEjemplarProductoImpl implements SvcEjemplarProducto {

    @Autowired
    private DaoEjemplarProducto repoEProducto;

    @Override
    public void crearEjemplar(EjemplarProducto item) {
        repoEProducto.save(item);
    }
}
