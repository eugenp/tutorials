package com.baeldung.beaninjection.beantypes.config;

import com.baeldung.beaninjection.beantypes.Services.CustomerService;
import com.baeldung.beaninjection.beantypes.Services.ProductService;
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
    public PriceList priceList() {
        return new PriceList("medium");
    }
}
