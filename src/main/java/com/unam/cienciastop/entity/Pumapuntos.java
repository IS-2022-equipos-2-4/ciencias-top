package com.unam.cienciastop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@Table(name="pumapuntos")
public class Pumapuntos {
    @Id
    private Integer idPumapuntos;

    @Column(name = "mes")
    private Integer mes;

    @Column(name = "saldo")
    private Integer saldo;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_usuario")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Usuario usuario;

    public Pumapuntos() {}

    public Pumapuntos(int mes, int saldo, Usuario usuario) {
        this.mes = mes;
        this.saldo = saldo;
        this.usuario = usuario;
    }
    
    public Integer getIdPumapuntos() {
        return idPumapuntos;
    }

    public void setIdPumapuntos(Integer idPumapuntos) {
        this.idPumapuntos = idPumapuntos;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getSaldo() {
        return saldo;
    }

    public void setSaldo(Integer saldo) {
        this.saldo = saldo;
    }
}