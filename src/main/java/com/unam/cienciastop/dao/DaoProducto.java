package com.unam.cienciastop.dao;

import org.springframework.data.repository.CrudRepository;

import com.unam.cienciastop.entity.Producto;

public interface DaoProducto extends CrudRepository<Producto,Integer>{
    
}
