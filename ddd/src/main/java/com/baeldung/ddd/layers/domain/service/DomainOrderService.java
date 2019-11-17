package com.baeldung.ddd.layers.domain.service;

import com.baeldung.ddd.layers.domain.Order;
import com.baeldung.ddd.layers.domain.Product;
import com.baeldung.ddd.layers.domain.repository.OrderRepository;
import org.bson.types.ObjectId;

import java.util.UUID;

public class DomainOrderService implements OrderService {

    private final OrderRepository orderRepository;

    public DomainOrderService(final OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public ObjectId createOrder(final Product product) {
        final Order order = new Order(ObjectId.get(), product);
        orderRepository.save(order);

        return order.getId();
    }

    @Override
    public void addProduct(final ObjectId id, final Product product) {
        final Order order = getOrder(id);
        order.addOrder(product);

        orderRepository.save(order);
    }

    @Override
    public void completeOrder(final ObjectId id) {
        final Order order = getOrder(id);
        order.complete();

        orderRepository.save(order);
    }

    @Override
    public void deleteProduct(final ObjectId id, final UUID productId) {
        final Order order = getOrder(id);
        order.removeOrder(productId);

        orderRepository.save(order);
    }

    private Order getOrder(ObjectId id) {
        return orderRepository
          .findById(id)
          .orElseThrow(() -> new RuntimeException("Order with given id doesn't exist"));
    }
}
