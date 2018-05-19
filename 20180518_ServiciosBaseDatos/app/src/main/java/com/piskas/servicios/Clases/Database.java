package com.piskas.servicios.Clases;

import java.util.ArrayList;

public final class Database {
    private final static ArrayList<Cliente> clientes = new ArrayList<>();
    private final static ArrayList<Servicio> servicios = new ArrayList<>();


    // CONSTRUCTOR =================================================================================
    private Database() { }


    // SELECT ======================================================================================
    public static Cliente[] GetClientes() {
        Cliente[] result = new Cliente[clientes.size()];
        result = clientes.toArray(result);
        return result;
    }
    public static Servicio[] GetServicios() {
        Servicio[] result = new Servicio[servicios.size()];
        result = servicios.toArray(result);
        return result;
    }
    public static Cliente GetClienteRut(String rut) {
        for (Cliente cliente: clientes) {
            if (cliente.getRut().equals(rut)) {
                return cliente;
            }
        }
        return null;
    }
    public static Servicio GetServicioCodigo(int codigo) {
        for (Servicio servicio: servicios) {
            if (servicio.getCodigo() == codigo) {
                return servicio;
            }
        }
        return null;
    }


    // INSERT ======================================================================================
    public static boolean Insert(Cliente cliente) {
        if (cliente.getRut().isEmpty()) return false;
        if (GetClienteRut(cliente.getRut()) != null) return false;
        clientes.add(cliente);
        return true;
    }
    public static boolean Insert(Servicio servicio) {
        if (servicio.getCodigo() == 0) return false;
        if (GetServicioCodigo(servicio.getCodigo()) != null) return false;
        servicios.add(servicio);
        return true;
    }


    // UPDATE ======================================================================================
    public static boolean Update(Cliente cliente) {
        if (cliente.getRut().isEmpty()) return false;
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getRut().equals(cliente.getRut())) {
                clientes.set(i, cliente);
                return true;
            }
        }
        return false;
    }
    public static boolean Update(Servicio servicio) {
        if (servicio.getCodigo() == 0) return false;
        for (int i = 0; i < servicios.size(); i++) {
            if (servicios.get(i).getCodigo() == servicio.getCodigo()) {
                servicios.set(i, servicio);
                return true;
            }
        }
        return false;
    }


    // DELETE ======================================================================================
    public static boolean DeleteClienteRut(String rut) {
        if (rut.isEmpty()) return false;
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getRut().equals(rut)) {
                clientes.remove(i);
                return true;
            }
        }
        return false;
    }
    public static boolean DeleteServicioCodigo(int codigo) {
        if (codigo == 0) return false;
        for (int i = 0; i < servicios.size(); i++) {
            if (servicios.get(i).getCodigo() == codigo) {
                clientes.remove(i);
                return true;
            }
        }
        return false;
    }
}
