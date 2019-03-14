package com.baeldung.hexagonal.store.persistence.repo.order;

import com.baeldung.hexagonal.store.core.context.order.entity.OrderProduct;
import com.baeldung.hexagonal.store.core.context.order.entity.OrderProductId;
import com.baeldung.hexagonal.store.core.context.order.infrastructure.OrderProductDataStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OrderProductRepositoryImpl implements OrderProductDataStore {
    @Autowired
    private OrderProductRepository orderProductRepository;

    @Override
    public OrderProduct save(OrderProduct orderProduct) {
        return this.orderProductRepository.save(orderProduct);
    }

    @Override
    public Optional<OrderProduct> findById(OrderProductId orderProductPkId) {
        return this.orderProductRepository.findById(orderProductPkId);
    }
}
