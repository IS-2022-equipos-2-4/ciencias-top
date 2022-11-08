package com.unam.cienciastop.service;

import com.unam.cienciastop.entity.Pumapuntos;

public interface SvcPumapuntos {
    Pumapuntos getPumapuntos(Integer idUsuario);
    void sumarPumapuntos(Integer idUsuario, Integer cantidad);
}
