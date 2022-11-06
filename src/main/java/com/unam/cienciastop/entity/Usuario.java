package com.unam.cienciastop.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name="usuarios",
        uniqueConstraints = @UniqueConstraint(
            columnNames = {"correo","num_institucional"}))
public class Usuario implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer id; 

    @Column(name="nombre",length = 50)
    private String nombre;

    @Column(name="correo")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9]+.unam.mx$", message = "correo invalido")
    private String correo;

    @Column(name="contraseña")
    @Pattern(regexp = "[A-Za-z0-9._-]$",message = "carcateres invalidos")
    private String contraseña;

    @Column(name="num_institucional",length = 9)
    @Pattern(regexp = "[0-9]", message = "no. de cuenta invalido")
    private String numInstitucional;

    @Column(name="carrera")
    @ColumnDefault(value = "'No aplica'")
    private String carrera;

    @Column(name="telefono",length = 10)
    @Pattern(regexp = "[0-9]{10}", message = "telefono invalido")
    private String telefono;

    @Column(name="activo")
    private Boolean activo;

    @Column(name="esProveedor")
    private Boolean esProveedor;

    @Column(name="esAdmin")
    private Boolean esAdmin;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getNumInstitucional() {
        return numInstitucional;
    }

    public void setNumInstitucional(String numInstitucional) {
        this.numInstitucional = numInstitucional;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Boolean getEsProveedor() {
        return esProveedor;
    }

    public void setEsProveedor(Boolean esProveedor) {
        this.esProveedor = esProveedor;
    }

    public Boolean getEsAdmin() {
        return esAdmin;
    }

    public void setEsAdmin(Boolean esAdmin) {
        this.esAdmin = esAdmin;
    }

    
}
