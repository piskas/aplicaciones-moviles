package com.piskas.recordatorios;

public class Recordatorio {
    private String titulo;
    private String recordatorio;

    public Recordatorio(String titulo, String recordatorio) {
        this.titulo = titulo;
        this.recordatorio = recordatorio;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getRecordatorio() {
        return recordatorio;
    }

    public void setRecordatorio(String recordatorio) {
        this.recordatorio = recordatorio;
    }

    @Override
    public String toString() {
        return titulo;
    }
}
