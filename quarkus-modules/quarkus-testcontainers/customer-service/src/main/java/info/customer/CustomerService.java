package info.customer;

import info.customer.client.Order;
import info.customer.client.OrderService;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

@ApplicationScoped
public class CustomerService {

    @RestClient
    OrderService orderService;

    public Customer getCustomer(Long id) {
        Customer customer = Customer.findById(id);
        List<Order> orders = orderService.findByCustomerId(id).stream().toList();
        customer.addOrders(orders);
        return customer;
    }

}
