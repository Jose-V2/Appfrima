package com.uth.appfrima;

public class Signature {
    private int id;
    private String descripcion;
    private byte[] firmaDigital;

    public Signature() {}

    public Signature(int id, String descripcion, byte[] firmaDigital) {
        this.id = id;
        this.descripcion = descripcion;
        this.firmaDigital = firmaDigital;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public byte[] getFirmaDigital() {
        return firmaDigital;
    }

    public void setFirmaDigital(byte[] firmaDigital) {
        this.firmaDigital = firmaDigital;
    }
}
