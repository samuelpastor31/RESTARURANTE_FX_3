package es.progcipfpbatoi.exceptions;

public class AlreadyServedOrderException extends RuntimeException {
    public AlreadyServedOrderException(String msg) {
        super(msg);
    }
}
