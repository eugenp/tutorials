package hexagonal.infrastructure.persistence.inmemory.config;

import hexagonal.domain.spi.OrderPersistencePort;
import hexagonal.infrastructure.persistence.inmemory.adapter.OrderInMemoryAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InMemoryAdapterConfiguration {

    @Bean
    public OrderPersistencePort getOrderPersistencePort() {
        return new OrderInMemoryAdapter();
    }
}
