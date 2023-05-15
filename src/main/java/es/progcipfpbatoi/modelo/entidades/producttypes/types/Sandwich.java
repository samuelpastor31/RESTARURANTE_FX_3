package es.progcipfpbatoi.modelo.entidades.producttypes.types;

import es.progcipfpbatoi.modelo.entidades.producttypes.Product;

public class Sandwich extends Product {

    public Sandwich(String cod, String name, float precio, float descuento, float iva, boolean dadoBaja) {
        super(cod, name,precio,descuento,iva,dadoBaja);
    }

    @Override
    public String getTipo() {
        return "SANDWICH";
    }
}
