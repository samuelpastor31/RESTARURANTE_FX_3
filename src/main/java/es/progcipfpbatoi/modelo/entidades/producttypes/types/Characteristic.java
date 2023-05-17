package es.progcipfpbatoi.modelo.entidades.producttypes.types;

public enum Characteristic {
    DIABETIC_SUITABLE {
        @Override
        public String toString(){
            return "DIABETIC_SUITABLE";
        }
    }, CELIAC_SUITABLE {
        @Override
        public String toString(){
            return "CELIAC_SUITABLE";
        }
    }, NO_APTO {
        @Override
        public String toString() {
            return "NO_APTO";
        }
    }
}
