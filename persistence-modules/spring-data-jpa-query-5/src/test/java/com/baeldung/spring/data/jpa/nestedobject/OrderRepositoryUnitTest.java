package com.baeldung.spring.data.jpa.nestedobject;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("h2")
@SpringBootTest(classes = OrderApplication.class)
public class OrderRepositoryUnitTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void whenFindByCustomerEmail_thenReturnOrders() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John");
        customer.setEmail("john@example.com");
        customerRepository.save(customer);

        Order order = new Order();
        order.setId(1L);
        order.setOrderDate(new Date());
        order.setCustomer(customer);
        orderRepository.save(order);

        List<Order> foundOrders = orderRepository.findByCustomerEmail("john@example.com");
        assertEquals(1, foundOrders.size());
        assertEquals("John", foundOrders.get(0).getCustomer().getName());

        List<Order> foundOrders2 = orderRepository.findByCustomer_Email("john@example.com");
        assertEquals(1, foundOrders2.size());
        assertEquals("John", foundOrders2.get(0).getCustomer().getName());

        List<Order> foundOrders3 = orderRepository.findByCustomerEmailAndJPQL("john@example.com");
        assertEquals(1, foundOrders3.size());
        assertEquals("John", foundOrders3.get(0).getCustomer().getName());
    }
}