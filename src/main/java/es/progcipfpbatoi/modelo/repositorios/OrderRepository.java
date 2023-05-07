package es.progcipfpbatoi.modelo.repositorios;

import es.progcipfpbatoi.exceptions.NotFoundException;
import es.progcipfpbatoi.modelo.entidades.Order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface OrderRepository {

    void add(Order order);
    int size();

    ArrayList<Order> findAll();

    Order getByCod(String code) throws NotFoundException;

    List<Order> getByDate(LocalDateTime date) throws NotFoundException;
}
