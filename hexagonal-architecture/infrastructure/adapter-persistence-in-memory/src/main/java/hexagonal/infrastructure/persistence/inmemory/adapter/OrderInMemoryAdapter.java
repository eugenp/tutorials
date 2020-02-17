package hexagonal.infrastructure.persistence.inmemory.adapter;

import hexagonal.domain.model.Order;
import hexagonal.domain.spi.OrderPersistencePort;

import java.util.HashMap;
import java.util.Map;

public class OrderInMemoryAdapter implements OrderPersistencePort {

    private static final Map<Integer, Order> orderMap = new HashMap<Integer, Order>(0);

    @Override
    public void addOrder(Order order) {
        orderMap.put(order.getOrderId(), order);
    }

}
