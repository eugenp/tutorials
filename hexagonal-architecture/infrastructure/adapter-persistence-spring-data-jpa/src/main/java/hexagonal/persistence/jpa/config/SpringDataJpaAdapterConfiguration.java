package hexagonal.persistence.jpa.config;

import hexagonal.domain.spi.OrderPersistencePort;
import hexagonal.persistence.jpa.adapter.OrderSpringJpaAdapter;
import hexagonal.persistence.jpa.repository.OrderRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDataJpaAdapterConfiguration {

    @Bean
    public OrderPersistencePort getOrderPersistencPort(OrderRepository orderRepository) {
        return new OrderSpringJpaAdapter(orderRepository);
    }
}
