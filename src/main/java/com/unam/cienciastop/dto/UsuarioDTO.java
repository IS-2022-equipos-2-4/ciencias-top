package com.unam.cienciastop.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UsuarioDTO {
    @NotNull(message = "No pusiste nombre")
    private String nombre;

    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9]+.unam.mx$",message = "correo invalido debe tener terminación unam.mx")
    @NotNull(message = "No pusiste correo")
    private String correo;

    @Pattern(regexp = "[0-9]{10}", message = "telefono invalido")
    @NotNull(message = "No pusiste telefono")
    private String telefono;
    
    private Boolean esProveedor;
    private Boolean esAdmin;

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return this.correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Boolean isEsProveedor() {
        return this.esProveedor;
    }

    public Boolean getEsProveedor() {
        return this.esProveedor;
    }

    public void setEsProveedor(Boolean esProveedor) {
        this.esProveedor = esProveedor;
    }

    public Boolean isEsAdmin() {
        return this.esAdmin;
    }

    public Boolean getEsAdmin() {
        return this.esAdmin;
    }

    public void setEsAdmin(Boolean esAdmin) {
        this.esAdmin = esAdmin;
    }
}