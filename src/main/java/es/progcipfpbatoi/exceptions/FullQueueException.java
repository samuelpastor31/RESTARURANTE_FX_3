package es.progcipfpbatoi.exceptions;

public class FullQueueException extends RuntimeException {

    public FullQueueException(int valorMaximo) {
        super(String.format("La cola está llena con %d elementos", valorMaximo));
    }
}
