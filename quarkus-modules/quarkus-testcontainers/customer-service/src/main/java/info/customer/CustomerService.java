package info.customer;

import java.util.List;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import info.customer.client.Order;
import info.customer.client.OrderService;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CustomerService {

    @RestClient
    OrderService orderService;

    public Customer getCustomer(Long id) {
        Customer customer = Customer.findById(id);
        List<Order> orders = orderService.findByCustomerId(id)
            .stream()
            .toList();
        customer.addOrders(orders);
        return customer;
    }

}
