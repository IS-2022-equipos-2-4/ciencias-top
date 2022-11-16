package com.unam.cienciastop.dto;

public class UsuarioDTO {
    private Integer id;
    private String nombre;
    private String correo;
    private String telefono;
    private Boolean esProveedor;
    private Boolean esAdmin;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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