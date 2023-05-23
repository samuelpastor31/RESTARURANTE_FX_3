package es.progcipfpbatoi.modelo.entidades.producttypes.types;

import es.progcipfpbatoi.modelo.entidades.producttypes.Product;

public class Starter extends Product {

    private int ration;

    public Starter(String cod, String name, float precio, float descuento, float iva, boolean dadoBaja) {
        super(cod, name, precio, descuento, iva, dadoBaja);
    }

    public int getRation() {
        return ration;
    }

    @Override
    public String getTipo() {
         return "STARTER";
    }

    @Override
    public int getTipoBD() {
        return 2;
    }

    @Override
    public String getExtras() {
        return String.format("Raciones: %s", ration);
    }
}
