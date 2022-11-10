package com.unam.cienciastop.service;

import com.unam.cienciastop.entity.Pumapuntos;

public interface SvcPumapuntos {
    /** Regresa el saldo(pumapuntos) de un usuario */
    int getPumapuntos(Integer idUsuario);

    /** Suma puma puntos al idUsuario seleccionado, la cantidad puede ser negativa.  */
    void sumarPumapuntos(Integer idUsuario, Integer cantidad);
}
