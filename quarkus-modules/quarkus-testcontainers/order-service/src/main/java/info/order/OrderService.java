package info.order;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class OrderService {

    public List<Order> findByCustomerId(Long id) {
        return Order.find("customer.id = ?1", id).list();
    }

}
