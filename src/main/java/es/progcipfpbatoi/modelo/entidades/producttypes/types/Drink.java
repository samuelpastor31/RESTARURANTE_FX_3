package es.progcipfpbatoi.modelo.entidades.producttypes.types;

import es.progcipfpbatoi.modelo.entidades.producttypes.Product;

public class Drink extends Product {

    private boolean refillable;

    private Size size;

    public Drink(String cod, String name, float prize, float disccount, float vat, boolean dadoAlta, boolean  refillable,Size size) {
        super(cod, name, prize, disccount, vat,dadoAlta);
        this.refillable = refillable;
        this.size = size;
    }

    public Drink(String cod, String name, float prize, float disccount, float vat, boolean dadoAlta) {
        super(cod, name, prize, disccount, vat,dadoAlta);
        this.refillable = refillable;
        this.size = size;
    }

    @Override
    public int getTipoBD() {
        return 1;
    }
    public Size getSize() {
        return size;
    }

    public void setRefillable(boolean refillable) {
        this.refillable = refillable;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public boolean isRefillable() {
        return refillable;
    }

    @Override
    public String getTipo() {
        return "DRINK";
    }

    @Override
    public String toString() {
        return super.toString() +
                ", Rellenable: " + refillable +
                ", Tamanyo: " + size;
    }

    @Override
    public String getExtras() {
        return String.format("Rellenable: %b, Tamaño: %s", (refillable)? "Sí": "No", size);
    }
}
