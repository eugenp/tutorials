package com.baeldung.beaninjection.beantypes.config;

import com.baeldung.beaninjection.beantypes.Services.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServicesConfig {
    @Bean
    public ProductService productService() {
        return new ProductService();
    }

    @Bean
    public CustomerService customerService() {
        return new CustomerService();
    }

    @Bean
    public PriceListService priceList() {
        return new PriceListService("clothes");
    }

    @Bean
    public OrderService orderService() {
        return new OrderService();
    }

    @Bean
    public ShoppingCartService shoppingCartService(ProductService productService, OrderService orderService) {
        return new ShoppingCartService(productService, orderService);
    }
}
