package com.baeldung.ddd.layers.domain.service;

import com.baeldung.ddd.layers.domain.Order;
import com.baeldung.ddd.layers.domain.Product;
import com.baeldung.ddd.layers.domain.repository.OrderRepository;
import org.bson.types.ObjectId;

public class DomainOrderService implements OrderService {

    private final OrderRepository orderRepository;

    public DomainOrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public ObjectId createOrder(final Product product) {
        final Order order = new Order(ObjectId.get(), product);
        orderRepository.save(order);

        return order.getId();
    }

    @Override
    public void addProduct(ObjectId id, Product product) {
        final Order order = getOrder(id);
        order.addProduct(product);

        orderRepository.save(order);
    }

    @Override
    public void completeOrder(ObjectId id) {
        final Order order = getOrder(id);
        order.complete();

        orderRepository.save(order);
    }

    @Override
    public void deleteProduct(ObjectId id, String name) {
        final Order order = getOrder(id);
        order.removeProduct(name);

        orderRepository.save(order);
    }

    private Order getOrder(ObjectId id) {
        return orderRepository
          .findById(id)
          .orElseThrow(() -> new RuntimeException("Order with given id doesn't exist"));
    }
}
