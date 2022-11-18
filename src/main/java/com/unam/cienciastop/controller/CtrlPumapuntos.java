package com.unam.cienciastop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unam.cienciastop.entity.Pumapuntos;
import com.unam.cienciastop.service.SvcPumapuntos;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class CtrlPumapuntos {

    @Autowired
    private SvcPumapuntos svcPuma;

    /**
     * @param idUsuario
     * @return
     */
    @GetMapping("/pumapuntos/{id_usuario}")
    public ResponseEntity<Integer> getPumapuntos(@PathVariable(value = "id_usuario") Integer idUsuario) {                
        return new ResponseEntity<Integer>(svcPuma.getPumapuntos(idUsuario),HttpStatus.OK);
    }

    /**
     * @param idUsuario
     * @param cantidad
     * @return
     */
    @PostMapping("/pumapuntos/{id_usuario}/sumar/{cantidad}")
    public ResponseEntity<Integer> sumarPumapuntos(
        @PathVariable(value = "id_usuario") Integer idUsuario,
        @PathVariable(value = "cantidad") Integer cantidad) {
        return new ResponseEntity<Integer>(svcPuma.sumarPumapuntos(idUsuario, cantidad), HttpStatus.OK);        
    }
}

