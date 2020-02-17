package hexagonal.application.service.adapter;

import hexagonal.application.service.api.OrderService;
import hexagonal.domain.model.Order;
import hexagonal.domain.spi.OrderPersistencePort;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderServiceAdapter implements OrderService {

    private OrderPersistencePort orderPersistencePort;

    @Autowired
    public OrderServiceAdapter(OrderPersistencePort orderPersistencePort) {
        this.orderPersistencePort = orderPersistencePort;
    }

    @Override
    public void createOrder(Order order) {
        orderPersistencePort.addOrder(order);
    }

}
