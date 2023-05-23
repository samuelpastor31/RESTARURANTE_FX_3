package es.progcipfpbatoi.modelo.dao;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.exceptions.NotFoundException;
import es.progcipfpbatoi.modelo.entidades.Order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface OrderDao {

    void add(Order order);
    int size();

    ArrayList<Order> findAll() throws DatabaseErrorException;

    Order getByCod(String code) throws NotFoundException;

    List<Order> getByDate(LocalDateTime date) throws NotFoundException;
}
