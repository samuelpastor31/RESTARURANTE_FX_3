package es.progcipfpbatoi.modelo.dao;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.exceptions.NotFoundException;
import es.progcipfpbatoi.modelo.entidades.producttypes.Product;

import java.io.IOException;
import java.util.ArrayList;

public interface ProductosDAO {
    ArrayList<Product> findAll(Class<? extends Product> productClass) throws DatabaseErrorException;

    /**
     *  Obtiene todas las tareas
     */
    ArrayList<Product> findAll() throws DatabaseErrorException;

    ArrayList<Product> findAllAvailable() throws DatabaseErrorException;

    Product findByCod(int id) throws DatabaseErrorException;

    public Product getByCod(int id) throws NotFoundException, DatabaseErrorException;

    /**
     * Almacena la tarea o la actualiza en caso de existir
     *
     * @param product
     * @throws DatabaseErrorException
     */
    void save(Product product) throws DatabaseErrorException, IOException;

    boolean remove(int id) throws DatabaseErrorException, NotFoundException;


    boolean isNameExists(String productName);
}
