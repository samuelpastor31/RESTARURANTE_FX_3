package es.progcipfpbatoi.modelo.entidades.producttypes.types;

import es.progcipfpbatoi.modelo.entidades.producttypes.Product;

public class Starter extends Product {

    private int ration;

    public Starter(String cod, String name) {

        super(cod, name, "e");
        this.ration = 1;
    }

    public int getRation() {
        return ration;
    }

    @Override
    public String getExtras() {
        return String.format("Raciones: %s", ration);
    }
}
