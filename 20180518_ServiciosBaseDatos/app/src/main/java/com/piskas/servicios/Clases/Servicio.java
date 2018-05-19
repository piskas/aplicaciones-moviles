package com.piskas.servicios.Clases;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

public class Servicio implements Serializable {
    private int codigo, valor;
    private String descripcion;

    public Servicio(int codigo, int valor, String descripcion) {
        this.codigo = codigo;
        this.valor = valor;
        this.descripcion = descripcion;
    }

    public int getCodigo() {
        return codigo;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return this.descripcion + " (" + NumberFormat.getCurrencyInstance(new Locale("es", "CL")).format(this.valor) +")";
    }
}
