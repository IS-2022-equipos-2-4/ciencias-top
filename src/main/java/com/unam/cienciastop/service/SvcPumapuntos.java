package com.unam.cienciastop.service;

import com.unam.cienciastop.entity.Pumapuntos;

public interface SvcPumapuntos {
    /** Regresa el saldo(pumapuntos) de un usuario */
    Integer getPumapuntos(Integer idUsuario);

    /** Suma puma puntos al idUsuario seleccionado, la cantidad puede ser negativa.  */
    Integer sumarPumapuntos(Integer idUsuario, Integer cantidad);
}
