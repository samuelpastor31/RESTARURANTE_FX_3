package es.progcipfpbatoi.modelo.entidades.producttypes.types;

import es.progcipfpbatoi.modelo.entidades.producttypes.Product;

public class Drink extends Product {

    private boolean refillable;

    private Size size;

    public Drink(String cod, String name, float prize, float disccount, float vat) {
        super(cod, name, prize, disccount, vat, "b");
        this.refillable = false;
        this.size = Size.NORMAL;
    }

    public Drink(String cod, String name) {
        this(cod, name, 1.0f, 0f, 0.1f);
    }

    public Size getSize() {
        return size;
    }

    public boolean isRefillable() {
        return refillable;
    }

    @Override
    public String getExtras() {
        return String.format("Rellenable: %b, Tamaño: %s", (refillable)? "Sí": "No", size);
    }
}
