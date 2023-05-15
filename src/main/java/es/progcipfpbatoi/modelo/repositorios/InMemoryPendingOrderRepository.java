package es.progcipfpbatoi.modelo.repositorios;

import es.progcipfpbatoi.exceptions.EmptyQueueException;
import es.progcipfpbatoi.exceptions.FullQueueException;
import es.progcipfpbatoi.modelo.entidades.Order;
import es.progcipfpbatoi.modelo.entidades.producttypes.Product;
import es.progcipfpbatoi.modelo.entidades.producttypes.types.Drink;
import es.progcipfpbatoi.modelo.entidades.producttypes.types.Size;

import java.util.ArrayList;

public class InMemoryPendingOrderRepository extends InMemoryArchiveHistoryOrderRepository implements QueueOrderRepository{

    private static final int MAX_PEDIDOS = 100;

    public InMemoryPendingOrderRepository() {
        super();
        init();
    }

    private void init(){
        Product product = new Drink("0","Cocacola",1,1,0,true,true, Size.NORMAL);
        Order order = new Order("0","Samuel");
        order.addNewProduct(product);
        this.orderList.add(order);
    }
    @Override
    public void add(Order order) {
        if (super.size() < MAX_PEDIDOS) {
            super.add(order);
            return;
        }

        throw new FullQueueException(MAX_PEDIDOS);
    }

    public Order getNext() {
        if (existsOrders()) {
            Order order = orderList.get(0);
            orderList.remove(order);
            return order;
        }

        throw new EmptyQueueException();
    }

    public void relocate(Order order) {
        orderList.add(0, order);
    }

    @Override
    public boolean isFull() {
        return orderList.size() == MAX_PEDIDOS;
    }

    public boolean existsOrders() {
        return orderList.size() > 0;
    }

    public void remove(Order order) {
        orderList.remove(order);
    }
}
