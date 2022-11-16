package com.unam.cienciastop.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "hibernateLazyInitializer" })
@Entity
@Table(name = "productos")
public class Producto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Integer idProducto;

    @Column(name = "codigo", nullable = false, length = 12, unique = true)
    @Pattern(regexp = "[A-Z0-9]{12}", message = "el codigo debe tener 12 caracteres y solo pueden ser mayusculas y numeros")
    private String codigo;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "costo", nullable = false)
    @Min(value = 0, message = "el costo no puede ser negativo")
    private Integer costo;

    // cantidad de productos disponibles para rentar
    @Column(name = "stock", nullable = false)
    @Min(value = 1, message = "el minimo de stock es 1")
    private Integer stock;

    @Column(name = "limite_prestamo", nullable = false)
    @Min(value = 3, message = "el limite de prestamo debe ser un valor entre 3 y 7")
    @Max(value = 7, message = "el limite de prestamo debe ser un valor entre 3 y 7")
    private Integer limitePrestamo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proveedor", referencedColumnName = "id_usuario", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Usuario proveedor;

    public Integer getId() {
        return idProducto;
    }

    public void setId(Integer id) {
        this.idProducto = id;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Usuario getProveedor() {
        return proveedor;
    }

    public void setProveedor(Usuario proveedor) {
        this.proveedor = proveedor;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCosto() {
        return costo;
    }

    public void setCosto(Integer costo) {
        this.costo = costo;
    }

    public Integer getLimitePrestamo() {
        return limitePrestamo;
    }

    public void setLimitePrestamo(Integer limitePrestamo) {
        this.limitePrestamo = limitePrestamo;
    }
}
