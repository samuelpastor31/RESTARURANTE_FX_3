package es.progcipfpbatoi.modelo.entidades.producttypes;

public abstract class Product {

    private String cod;

    private String name;

    private float prize;

    private float discount;

    private float vat;

    private String prefixCode;

    public Product(String cod, String name, float prize, float disccount, float vat, String prefixCode) {
        this.cod = prefixCode + cod;
        this.name = name;
        this.prize = prize;
        this.discount = disccount;
        this.vat = vat;
        this.prefixCode = prefixCode;
    }

    public Product(String cod, String name, String prefixCode) {
        this(cod, name, 1.25f, 0f, 0.1f, prefixCode);
    }
    public Product(String cod, String name, String prefixCode, float discount) {
        this(cod, name, 1.25f, discount, 0.1f, prefixCode);
    }

    @Override
    public String toString() {
        return getName()+", Descuento: "+getPercentageDiscount()+"%, Precio: "+getPrizeWithoutDiscount()+"€, Precio con IVA: "+getPrize()+"€";
    }

    public String getCod() {
        return this.cod;
    }

    public float getPrize() {
        return prize * (1 + vat) - (prize * discount);
    }

    public float getPrizeWithoutDiscount() {
        return prize * (1 + vat);
    }


    public String getName() {
        return name;
    }

    public void setDiscount(float disccount) {
        this.discount = disccount;
    }

    public float getPercentageDiscount() {
        return discount * 100;
    }

    public boolean containsThisCode(String cod) {
        return this.cod.equals(cod);
    }


    public String getExtras() {
        return "";
    }
}
