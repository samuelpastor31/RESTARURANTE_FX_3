package es.progcipfpbatoi.modelo.repositorios;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.exceptions.NotFoundException;
import es.progcipfpbatoi.modelo.dao.OrderDao;
import es.progcipfpbatoi.modelo.entidades.Order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository {

    private OrderDao orderDao;

    public void add(Order order){
         orderDao.add(order);
    }
    int size(){
       return orderDao.size();
    }

    ArrayList<Order> findAll() throws DatabaseErrorException {
        return orderDao.findAll();
    }

    Order getByCod(String code) throws NotFoundException {
        return orderDao.getByCod(code);
    }

    List<Order> getByDate(LocalDateTime date) throws NotFoundException {
        return orderDao.getByDate(date);
    }
}
