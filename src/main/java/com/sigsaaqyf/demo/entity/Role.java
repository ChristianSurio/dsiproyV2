package com.sigsaaqyf.demo.entity;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;
//ATRIBUTOS
//##################################################    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @Column
    private String nombre;
    
    @Column
    private String descripcion;
//##################################################

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "role [descripcion=" + descripcion + ", id=" + id + ", nombre=" + nombre + "]";
    }

    public Role(){
        
    }
    public Role(Optional<Role> role) {
        this.id = role.get().getId();
        this.nombre = role.get().getNombre();
        this.descripcion = role.get().getDescripcion();
    }

    
}