package org.baeldung.hexagonal.architecture;

import org.baeldung.hexagonal.architecture.adapter.secondary.ProductJpaAdapter;
import org.baeldung.hexagonal.architecture.port.inbound.ProductServicePort;
import org.baeldung.hexagonal.architecture.port.outbound.ProductPersistencePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

	@Bean
    public ProductPersistencePort productPersistence(){
        return new ProductJpaAdapter();
    }

    @Bean
    public ProductServicePort productService(){
        return new ProductServiceImpl(productPersistence());
    }
}
