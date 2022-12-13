package com.unam.cienciastop.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.unam.cienciastop.entity.Pumapuntos;

public interface DaoPumapuntos extends CrudRepository<Pumapuntos,Integer>{
    // query el valor de la columna mas la candidad que suma o resta
    // @Query("UPDATE pumapuntos(saldo) WHERE  id = :idUsuario VALUES(saldo + :cantidad)")
    // public void sumarPumapuntos(@Param("idUsuario") Integer idUsuario, @Param("cantidad") Integer cantidad);

     /**
     * Obtiene los pumapuntos de un usuario a través de su id
     * @param id_usuario
     * @return Pumapuntos
     */
    @Query(value = "SELECT * FROM pumapuntos WHERE id_usuario = :id_usuario", nativeQuery = true)
    public Pumapuntos getPumapuntos(@Param("id_usuario") Integer id_usuario);
}
