package es.progcipfpbatoi.modelo.entidades.producttypes;

public abstract class Product {

    private String cod;

    private String name;

    private float prize;

    private float discount;

    private float vat;

    private String prefixCode;

    private boolean dadoAlta;

    private String tipo;

    public Product(String cod, String name, float prize, float disccount, float vat,boolean dadoAlta) {
        this.cod =  cod;
        this.name = name;
        this.prize = prize;
        this.discount = disccount;
        this.vat = vat;
        this.dadoAlta = dadoAlta;
    }

    public abstract String getTipo();

    public float getDiscount() {
        return discount;
    }

    @Override
    public String toString() {
        return String.format("%s Tipo: %s, Descuento: %.2f%%, Precio: %.2f€, Precio con IVA: %.2f€, Dado alta: %s",
                getName(), getTipo(), getPercentageDiscount(), getPrizeWithoutDiscount(), getPrize(), isDadoAlta());
    }
    public boolean isDadoAlta() {
        return dadoAlta;
    }


    public void setCod(String cod) {
        this.cod = cod;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrize(float prize) {
        this.prize = prize;
    }

    public void setVat(float vat) {
        this.vat = vat;
    }

    public void setPrefixCode(String prefixCode) {
        this.prefixCode = prefixCode;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public float getVat() {
        return vat;
    }

    public String getCod() {
        return this.cod;
    }

    public void setDadoAlta(boolean dadoAlta) {
        this.dadoAlta = dadoAlta;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return cod.equals(product.cod);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
