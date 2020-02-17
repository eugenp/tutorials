package hexagonal.persistence.jpa.adapter;

import hexagonal.domain.model.Order;
import hexagonal.domain.spi.OrderPersistencePort;
import hexagonal.persistence.jpa.entity.OrderEntity;
import hexagonal.persistence.jpa.repository.OrderRepository;
import org.springframework.beans.BeanUtils;

public class OrderSpringJpaAdapter implements OrderPersistencePort {

    private OrderRepository orderRepository;

    public OrderSpringJpaAdapter(OrderRepository productRepository) {
        this.orderRepository = productRepository;
    }

    @Override
    public void addOrder(Order order) {
        OrderEntity orderEntity = new OrderEntity();
        BeanUtils.copyProperties(order, orderEntity);
        orderRepository.save(orderEntity);
    }
}
