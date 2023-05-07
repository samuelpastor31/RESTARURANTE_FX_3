package es.progcipfpbatoi.modelo.entidades.producttypes.types;

import es.progcipfpbatoi.exceptions.DiscountNotApplicableException;
import es.progcipfpbatoi.modelo.entidades.producttypes.Product;

import java.util.HashSet;
import java.util.List;

public class Desert extends Product {

   private HashSet<Characteristic> characteristic;

    public Desert(String cod, String name, Characteristic... characteristic) {
        this(cod, name, 0, characteristic);
    }

    public Desert(String cod, String name, float discount, Characteristic... characteristic) {
        super(cod, name, "p", discount);
        this.characteristic = new HashSet<>(List.of(characteristic));
        if (discount != 0) {
            throw new DiscountNotApplicableException();
        }
    }

    @Override
    public String getExtras() {
        return String.format("%s", characteristic);
    }

}
