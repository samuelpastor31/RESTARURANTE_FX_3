package es.progcipfpbatoi.exceptions;

public class NotCancelableOrderException extends RuntimeException{
    public NotCancelableOrderException(String msg) {
        super(msg);
    }
}
