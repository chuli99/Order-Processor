package ar.progamacion.julian.service.dto;

import ar.progamacion.julian.domain.Order;
import java.util.List;

public class OrdersDTO {

    private List<Order> orders;

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
