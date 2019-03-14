package com.baeldung.hexagonal.store.core.context.order.service;

import com.baeldung.hexagonal.store.core.context.customer.entity.Customer;
import com.baeldung.hexagonal.store.core.context.customer.exception.CustomerNotFoundException;
import com.baeldung.hexagonal.store.core.context.customer.exception.NotEnoughFundsException;
import com.baeldung.hexagonal.store.core.context.customer.infrastructure.CustomerDataStore;
import com.baeldung.hexagonal.store.core.context.order.infrastructure.EmailNotificationSender;
import com.baeldung.hexagonal.store.core.context.order.infrastructure.OrderDataStore;
import com.baeldung.hexagonal.store.core.context.order.entity.Order;
import com.baeldung.hexagonal.store.core.context.order.entity.OrderProduct;
import com.baeldung.hexagonal.store.core.context.order.entity.Product;
import com.baeldung.hexagonal.store.core.context.order.exception.ProductNotFoundException;
import com.baeldung.hexagonal.store.core.context.order.infrastructure.ProductDataStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private CustomerDataStore customerDataStore;
    private OrderDataStore orderDataStore;
    private ProductDataStore productDataStore;
    private EmailNotificationSender emailNotificationSender;

    @Autowired
    public OrderServiceImpl(CustomerDataStore customerDataStore, OrderDataStore orderDataStore, ProductDataStore productDataStore, EmailNotificationSender emailNotificationSender) {
        this.customerDataStore = customerDataStore;
        this.orderDataStore = orderDataStore;
        this.productDataStore = productDataStore;
        this.emailNotificationSender = emailNotificationSender;
    }

    @Override
    public Iterable<Order> getAllOrders() {
        return orderDataStore.findAll();
    }

    @Override
    public Optional<Order> getOrderById(int id) {
        return this.orderDataStore.findById((long) id);
    }

    @Override
    public Optional<Order> processNewCustomerOrder(long customerId, Map<Long, Integer> productQuantityMap)
            throws CustomerNotFoundException, ProductNotFoundException, NotEnoughFundsException {
        Optional<Customer> customer = this.customerDataStore.findById(customerId);
        if (!customer.isPresent()) {
            throw new CustomerNotFoundException();
        }
        Customer existingCustomer = customer.get();
        Order order = new Order();
        List<OrderProduct> orderProducts = new ArrayList<>();
        for (Map.Entry<Long, Integer> productEntry : productQuantityMap.entrySet()) {
            Optional<Product> product = productDataStore.findById(productEntry.getKey());
            if (!product.isPresent()) {
                throw new ProductNotFoundException();
            }
            OrderProduct orderProduct = new OrderProduct(order, product.get(), productEntry.getValue());
            orderProducts.add(orderProduct);
        }
        order.setOrderProducts(orderProducts);
        Double finalPrice = order.getTotalOrderPrice();
        if (!existingCustomer.hasEnoughFunds(finalPrice) && !existingCustomer.isNegativeBalanceAllowed()) {
            throw new NotEnoughFundsException();
        }
        existingCustomer.withdrawFunds(finalPrice);
        order.setStatus("Completed");
        order.setCustomer(existingCustomer);
        this.orderDataStore.save(order);
        this.notifyCustomerAboutNewOrder(existingCustomer, order);
        return Optional.of(order);
    }

    private void notifyCustomerAboutNewOrder(Customer customer, Order order) {
        this.emailNotificationSender.sendEmailMessage(
                customer.getEmail(),
                "New order " + order.getId() + " was created in the Baeldung store",
                "Order total is: " + order.getTotalOrderPrice());
    }
}
