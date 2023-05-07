package es.progcipfpbatoi.exceptions;

public class DiscountNotApplicableException extends RuntimeException{
    public DiscountNotApplicableException() {
        super("Descuento no aplicable a este tipo de producto");
    }
}
