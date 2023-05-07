package es.progcipfpbatoi.modelo.repositorios;


import es.progcipfpbatoi.exceptions.NotFoundException;
import es.progcipfpbatoi.modelo.entidades.Order;
import es.progcipfpbatoi.modelo.entidades.producttypes.Product;
import es.progcipfpbatoi.modelo.entidades.producttypes.types.Desert;
import es.progcipfpbatoi.modelo.entidades.producttypes.types.Drink;
import es.progcipfpbatoi.utils.DateConversion;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class InMemoryArchiveHistoryOrderRepository implements OrderRepository{

    protected ArrayList<Order> orderList;

    public InMemoryArchiveHistoryOrderRepository() {
        this.orderList = new ArrayList<>();
    }

    private void init(){

    };

    public String tamany(){
        return String.valueOf(this.orderList.size());
    }

    public void add(Order order) {
        this.orderList.add(order);
    }

    public int size() {
        return this.orderList.size();
    }

    public ArrayList<Order> findAll() {
        return orderList;
    }

    public Order getByCod(String code) throws NotFoundException {

        Order orderBuscada = new Order(code);
        if (orderList.contains(orderBuscada)) {
            return orderList.get(orderList.indexOf(orderBuscada));
        }

        throw new NotFoundException(String.format("El pedido con código %s no existe", code));
    }

    public List<Order> getByDate(LocalDateTime date) throws NotFoundException {
        List<Order> ordersOnDate = new ArrayList<>();
        for (Order order: orderList) {
            if (order.createdOnThisDate(date)) {
                ordersOnDate.add(order);
            }
        }

        if (ordersOnDate.size() > 0) {
            return ordersOnDate;
        }

        throw new NotFoundException(String.format("No hay datos para la fecha %s ", DateConversion.toString(date)));
    }
}
