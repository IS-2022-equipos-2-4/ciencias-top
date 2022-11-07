package com.unam.cienciastop.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name="historial_rentas")
public class HistorialRentas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_renta")
    private String idRenta;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_usuario")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Usuario usuario;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_ejemplar")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private EjemplarProducto itemProducto;

    @Column(name="fecha")
    private LocalDate fecha;

    // Indica si el producto rentado por el usuario ya fue devuelto
    @Column(name = "devuelto")
    private Boolean devuelto;

    public String getIdRenta() {
        return idRenta;
    }

    public void setIdRenta(String idRenta) {
        this.idRenta = idRenta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public EjemplarProducto getItemProducto() {
        return itemProducto;
    }

    public void setItemProducto(EjemplarProducto itemProducto) {
        this.itemProducto = itemProducto;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Boolean getDevuelto() {
        return devuelto;
    }

    public void setDevuelto(Boolean devuelto) {
        this.devuelto = devuelto;
    }
}
