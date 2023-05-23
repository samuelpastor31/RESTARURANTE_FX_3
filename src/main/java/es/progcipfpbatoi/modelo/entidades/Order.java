package es.progcipfpbatoi.modelo.entidades;

import es.progcipfpbatoi.exceptions.AlreadyServedOrderException;
import es.progcipfpbatoi.exceptions.NotCancelableOrderException;
import es.progcipfpbatoi.modelo.entidades.producttypes.Product;
import es.progcipfpbatoi.modelo.entidades.producttypes.Status;
import es.progcipfpbatoi.utils.DateConversion;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public class Order {

    private String code;

    private String clientName;

    private LocalDateTime createdOn;

    private ArrayList<Product> products;

    private Status status;
    private String cancelationReason;

    public Order(String code, String clientName) {
        this.clientName = clientName;
        this.createdOn = LocalDateTime.now();
        this.code = code;
        this.products = new ArrayList<>();
        this.status = Status.PENDING;
    }

    public Order(String code) {
        this.code = code;
    }

    public Order(LocalDateTime date) {
        this.createdOn = date;
    }

    public LocalDateTime getFechaPedido() {
        return createdOn;
    }

    public void addNewProduct(Product product) {
        products.add(product);
    }

    @Override
    public String toString() {
        return " Código : " + code +
                ", Nombre : " + clientName +
                ", Fecha : " + createdOn.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))+
                " ,Núm. productos : " +verProductos()  + "\n" +
                "Precio Total : " + getOrderPrize() +
                '€';
    }


    public String verProductos(){
        String productos=" ";
        for (int i = 0; i <products.size() ; i++) {
             productos = productos+ products.get(i).getCod().toString()+" ";
        }
        return productos;
    }

    public String getClientName() {
        return clientName;
    }

    public String getFormattedCreatedOn() {
        return DateConversion.toString(createdOn);
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public void setCanceled(String reason) {
        if (status == Status.SERVED) {
            throw new NotCancelableOrderException(String.format("El pedido %s no es cancelable por estar ya servido", this.code));
        }
        status = Status.CANCELED;
        this.cancelationReason = reason;
    }

    public void setServed() {
        if (status == Status.SERVED) {
            throw new AlreadyServedOrderException(String.format("El pedido %s ya fue servido anteriormente", this.code));
        }
        status = Status.SERVED;
    }

    public String getStatus() {
        return status.toString();
    }

    public String getCode() {
        return code;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public boolean hasProducts() {
        return products.size() > 0;
    }

    public double getOrderPrize() {
        double totalPrize = 0;
        for (Product product : products) {
            totalPrize += product.getPrize();
        }
        return totalPrize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Order order)) {
            return false;
        }
        return code.equals(order.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    public boolean createdOnThisDate(LocalDateTime date) {
        return this.createdOn.toLocalDate().isEqual(date.toLocalDate());
    }
}
