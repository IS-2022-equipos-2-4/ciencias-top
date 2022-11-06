package com.unam.cienciastop.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="historial_rentas")
public class HistorialRentas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_renta")
    private String idRenta;

    @OneToOne
    @JoinColumn(name="id_usuario")
    private Usuario usuario;

    @OneToOne
    @JoinColumn(name="id_ejemplar")
    private EjemplarProducto itemProducto;

    @Column(name="fecha")
    private LocalDate fecha;

}
