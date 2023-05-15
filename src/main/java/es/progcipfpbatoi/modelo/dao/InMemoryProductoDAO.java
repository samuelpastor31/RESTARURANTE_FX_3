package es.progcipfpbatoi.modelo.dao;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.exceptions.NotFoundException;
import es.progcipfpbatoi.modelo.entidades.producttypes.Product;

import java.io.IOException;
import java.util.ArrayList;

public class InMemoryProductoDAO implements ProductosDAO {

    private ArrayList<Product> products;

    public InMemoryProductoDAO() {
        this.products = new ArrayList<>();
    }


    @Override
    public ArrayList<Product> findAll(Class<? extends Product> productClass) {
        ArrayList<Product> productsFiltered = new ArrayList<>();
        for ( Product current : products ) {
            if ( current.getClass() == productClass ) {
                productsFiltered.add( current );
            }
        }

        return productsFiltered;
    }

    @Override
    public ArrayList<Product> findAll() {
        return this.products;
    }

    @Override
    public ArrayList<Product> findAllAvailable() throws DatabaseErrorException {
        return null;
    }

    @Override
    public void save(Product product) throws DatabaseErrorException, IOException {
    }

    @Override
    public boolean remove(int id) throws DatabaseErrorException, NotFoundException {
        return false;
    }

    @Override
    public boolean isNameExists(String productName) {
        return false;
    }

    @Override
    public Product getByCod(int id) throws NotFoundException, DatabaseErrorException {
        return null;
    }

    @Override
    public Product findByCod(int id) throws DatabaseErrorException{
        return null;
    }
}
