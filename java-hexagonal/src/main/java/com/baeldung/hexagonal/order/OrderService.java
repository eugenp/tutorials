package com.baeldung.hexagonal.order;

public interface OrderService {
    void save(Order order);
    void delete(Order order);
}
