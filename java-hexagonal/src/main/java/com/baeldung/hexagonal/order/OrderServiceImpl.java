package com.baeldung.hexagonal.order;

import com.baeldung.hexagonal.order.persistence.OrderPersistence;
import org.springframework.stereotype.Component;

@Component public class OrderServiceImpl implements OrderService {
        private OrderPersistence orderPersistence;

        public OrderServiceImpl(OrderPersistence orderPersistence) {
                this.orderPersistence = orderPersistence;
        }

        @Override public void save(Order order) {
                orderPersistence.save(order);
        }

        @Override public void delete(Order order) {
                orderPersistence.delete(order);
        }
}
