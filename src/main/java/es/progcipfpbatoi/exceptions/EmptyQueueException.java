package es.progcipfpbatoi.exceptions;

public class EmptyQueueException extends RuntimeException {
    public EmptyQueueException() {
        super("No se puede extraer ningún elemento de la cola. Está vacía");
    }
}
