package es.progcipfpbatoi.modelo.entidades.producttypes.types;

import es.progcipfpbatoi.exceptions.DiscountNotApplicableException;
import es.progcipfpbatoi.modelo.entidades.producttypes.Product;

import java.util.HashSet;
import java.util.List;

public class Desert extends Product {

   private HashSet<Characteristic> characteristic;


    public Desert(String cod, String name,float precio, float discount,float iva,boolean dadoAlta, Characteristic... characteristic) {
        super(cod, name, precio, discount,iva,dadoAlta);
        this.characteristic = new HashSet<>(List.of(characteristic));
        if (discount != 0) {
            throw new DiscountNotApplicableException();
        }
    }

    @Override
    public int getTipoBD() {
        return 4;
    }

    @Override
    public String getTipo() {
        return "DESERT";
    }

    public HashSet<Characteristic> getCharacteristic() {
        return characteristic;
    }

    @Override
    public String getExtras() {
        return String.format("%s", characteristic);
    }

    @Override
    public String toString() {
        return super.toString()+
                ", " + characteristic;
    }
}
