package hexagonal.application.service.config;

import hexagonal.application.service.adapter.OrderServiceAdapter;
import hexagonal.application.service.api.OrderService;
import hexagonal.domain.spi.OrderPersistencePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public OrderService getOrderService(OrderPersistencePort orderPersistencePort) {
        return new OrderServiceAdapter(orderPersistencePort);
    }
}
