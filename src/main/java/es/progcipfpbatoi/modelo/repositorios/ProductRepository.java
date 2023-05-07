package es.progcipfpbatoi.modelo.repositorios;

import es.progcipfpbatoi.exceptions.NotFoundException;
import es.progcipfpbatoi.modelo.entidades.producttypes.Product;

import java.util.ArrayList;

public interface ProductRepository {
    ArrayList<Product> findAll(Class<? extends Product> productClass);
    Product getByCod(String cod) throws NotFoundException;

}
