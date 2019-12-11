package com.example.enviarcorreocontactos;

import java.io.Serializable;

public class Contacto implements Serializable {
    private long id;
    private String nombre;
    private String numero;
    private String email;

    public Contacto(long id, String nombre, String numero, String email) {
        this.id = id;
        this.nombre = nombre;
        this.numero = numero;
        this.email = email;
    }

    public Contacto(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "ID: [ " + id + " ], NOMBRE: [ " + nombre + " ], NUMERO: [ " + numero + " ] \n";
    }

    public String toCSV(){
        return id + "," + nombre + "," + numero;
    }
}