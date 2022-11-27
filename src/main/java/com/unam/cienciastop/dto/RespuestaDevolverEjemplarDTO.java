package com.unam.cienciastop.dto;


public class RespuestaDevolverEjemplarDTO {
    private boolean devolucionTardia;

    public RespuestaDevolverEjemplarDTO(boolean devolucionTardia) {
        this.devolucionTardia = devolucionTardia;
    }

    public boolean getDevolucionTardia() {
        return this.devolucionTardia;
    }
}
