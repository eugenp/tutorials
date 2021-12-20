package com.baeldung.domain;

import com.baeldung.domain.model.CoffeeOrder;
import com.baeldung.domain.model.CoffeeType;
import com.baeldung.domain.model.OrderStatus;
import com.baeldung.domain.ports.OrderRepository;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

/**
 * Core of our application business.
 * Only this class contains the logic of how to handle and
 * deliver a coffee order.
 */
public class CoffeeOrderService {

    // Our business domain only references external APIs via interfaces (ports)
    private OrderRepository orderRepository;

    public CoffeeOrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Adds a coffee order as pending
     *
     * @param coffeeType
     * @param address
     */
    public void addOrder(String coffeeType, String address) {
        CoffeeOrder order = new CoffeeOrder()
          .setType(CoffeeType.valueOf(coffeeType.toUpperCase()))
          .setOrderAddress(address)
          .setOrderDate(new Date())
          .setStatus(OrderStatus.PENDING);
        final int orderId = orderRepository.create(order);
        order.setId(orderId);
    }

    /**
     * Optionally retrieves an existing coffee order by id, if it exists
     *
     * @param orderId
     * @return
     */
    public Optional<CoffeeOrder> findOrder(int orderId) {
        return orderRepository.findById(orderId);
    }

    public Collection<CoffeeOrder> findAll() {
        return orderRepository.findAll();
    }

    /**
     * Marks a pending coffee order as complete
     *
     * @param orderId
     * @return
     * @throws IllegalStateException
     */
    public boolean completeOrder(int orderId) throws IllegalStateException {
        return updateStatus(orderId, OrderStatus.COMPLETE);
    }

    /**
     * Marks a pending coffee order as canceled
     *
     * @param orderId
     * @return
     */
    public boolean cancelOrder(int orderId) throws IllegalStateException {
        return updateStatus(orderId, OrderStatus.CANCELED);
    }

    private boolean updateStatus(int orderId, OrderStatus status) {
        final Optional<CoffeeOrder> maybeOrder = findOrder(orderId);
        if (!maybeOrder.isPresent()) { // we do not expect an order completion action on a non existing order
            throw new IllegalStateException("Cannot cancel a non existing order");
        }
        final CoffeeOrder coffeeOrder = maybeOrder.get();
        if (coffeeOrder.updateStatus(status)) {
            orderRepository.update(coffeeOrder);
            return true;
        } else {
            return false;
        }
    }

}
