package org.baeldung.bean.dependencyinjection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("org.baeldung.bean.dependencyinjection")
public class DIConfig {

    @Bean
    public Product getProduct() {
        return new Product();
    }
}
