package es.progcipfpbatoi.modelo.entidades.producttypes;

public enum Status {

    PENDING {
        @Override
        public String toString() {
            return "Pendiente";
        }
    }, SERVED{
        @Override
        public String toString() {
            return "Servido";
        }
    }, CANCELED {
        @Override
        public String toString() {
            return "Cancelado";
        }
    }
}
