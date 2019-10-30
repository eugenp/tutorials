package com.baeldung.fetchMode;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class CustomerController {

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    public CustomerController(CustomerRepository customerRepository, OrderRepository orderRepository) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping("test/{id}")
    @Transactional
    public Set<Order> getCustomerOrders(@PathVariable Long id) {
        Customer customer = customerRepository.findById(id).get();
        return customer.getOrders();
    }

    @GetMapping("save")
    @Transactional
    public Long saveNewCustomer() {
        Customer customer = new Customer();
        customer = customerRepository.save(customer);

        Order order1 = orderRepository.save(new Order("order 1", customer));
        Order order2 = orderRepository.save(new Order("order 2", customer));
        Order order3 = orderRepository.save(new Order("order 3", customer));
        Order order4 = orderRepository.save(new Order("order 4", customer));
        Order order5 = orderRepository.save(new Order("order 5", customer));

        customer.getOrders().add(order1);
        customer.getOrders().add(order2);
        customer.getOrders().add(order3);
        customer.getOrders().add(order4);
        customer.getOrders().add(order5);

        Customer save = customerRepository.save(customer);
        return save.getId();
    }

}
