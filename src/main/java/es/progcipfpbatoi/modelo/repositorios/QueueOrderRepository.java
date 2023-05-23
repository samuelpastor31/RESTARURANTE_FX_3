package es.progcipfpbatoi.modelo.repositorios;

import es.progcipfpbatoi.modelo.dao.OrderDao;
import es.progcipfpbatoi.modelo.entidades.Order;

public interface QueueOrderRepository extends OrderDao {

        Order getNext();

        void relocate(Order order);

        boolean isFull();

        boolean existsOrders();

        void remove(Order order);
}
