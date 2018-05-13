package com.piskas.servicios.Clases;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Cliente implements Serializable {
    private String rut, nombre, apellido;
    private Date fechaNacimiento;
    private ArrayList<Servicio> servicios;

    public Cliente(String rut, String nombre, String apellido, Date fechaNacimiento) {
        this.rut = rut;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.servicios = new ArrayList<>();
    }

    public String getRut() {
        return rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombreCompleto() { return this.nombre + " " + this.apellido; }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCredito() {
        int result = 0;

        for (Servicio servicio: this.servicios) {
            result += servicio.getValor();
        }

        return NumberFormat.getCurrencyInstance(new Locale("es", "CL")).format(result);
    }

    public ArrayList<Servicio> getServicios() {
        return servicios;
    }

    public void setServicios(ArrayList<Servicio> servicios) {
        this.servicios = servicios;
    }

    public boolean setAgregarServicio(Servicio servicio) {
        try {
            servicios.add(servicio);
            return true;
        } catch(Exception ex) {
            return false;
        }
    }

    public boolean setEliminarServicio(int codigo) {
        try {
            for (int i = 0; i < servicios.size(); i++) {
                if (servicios.get(i).getCodigo() == codigo) {
                    servicios.remove(i);
                    return true;
                }
            }
            return false;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public String toString() {
        return rut;
    }
}
