package com.unam.cienciastop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unam.cienciastop.entity.Pumapuntos;
import com.unam.cienciastop.service.SvcPumapuntos;

@RestController
@RequestMapping("/api")
public class CtrlPumapuntos {
    
    @Autowired
    private SvcPumapuntos svcPuma;

    @GetMapping("/pumapuntos/{id_usuario}")
    public ResponseEntity<Pumapuntos> getPumapuntos(@PathVariable Integer idUsuario) {        
        return new ResponseEntity<>(svcPuma.getPumapuntos(idUsuario),HttpStatus.OK);
    }

    @PostMapping("/pumapuntos/{id_usuario}/sumar/{cantidad}")
    public ResponseEntity<Pumapuntos> sumarPumapuntos(
        @PathVariable Integer idUsuario,
        @PathVariable Integer cantidad) {
        return new ResponseEntity<>(svcPuma.sumarPumapuntos(idUsuario, cantidad), HttpStatus.OK);        
    }
}
