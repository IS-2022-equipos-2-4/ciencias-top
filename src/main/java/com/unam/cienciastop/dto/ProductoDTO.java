package com.unam.cienciastop.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class ProductoDTO {
    
    @Pattern(regexp = "[A-Z0-9]{12}", message = "el codigo debe tener 12 caracteres y solo pueden ser mayusculas y numeros")
    @NotNull(message = "Por favor, ingrese un código de producto")
    private String codigo;

    @NotNull(message = "Por favor, ingrese un nombre")
    private String nombre;

    @NotNull(message = "Por favor, ingrese una descripción")
    private String descripcion;

    @Min(value = 0, message = "Aprecio la caridad, pero el costo es positivo")
    @NotNull(message = "Por favor, ingrese un costo")
    private Integer costo;

    @NotNull(message = "Por favor, ingrese un número de stock")
    private Integer stock;

    @Min(value = 3, message = "El limite debe ser entre 3 y 7 días, por favor")
    @Max(value = 7, message = "El limite debe ser entre 3 y 7 días, por favor")
    @NotNull(message = "Por favor, ingrese un limite de días de prestamo")
    private Integer limitePrestamo;

    public String getCodigo() {
        return this.codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCosto() {
        return this.costo;
    }

    public void setCosto(Integer costo) {
        this.costo = costo;
    }

    public Integer getStock() {
        return this.stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getLimitePrestamo() {
        return this.limitePrestamo;
    }

    public void setLimitePrestamo(Integer limitePrestamo) {
        this.limitePrestamo = limitePrestamo;
    }
}