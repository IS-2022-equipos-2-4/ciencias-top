package com.unam.cienciastop.dao;

import org.springframework.data.repository.CrudRepository;

import com.unam.cienciastop.entity.Pumapuntos;

public interface DaoPumapuntos extends CrudRepository<Pumapuntos,Integer>{
    // query el valor de la columna mas la candidad que suma o resta
    @Query("UPDATE pumapuntos(saldo) WHERE  id = :idUsuario VALUES(saldo + :cantidad)")
    void sumarPumapuntos(Integer idUsuario, Integer cantidad);
}
