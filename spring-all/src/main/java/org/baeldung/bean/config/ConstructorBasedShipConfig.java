package org.baeldung.bean.config;

import org.baeldung.bean.injection.Helm;
import org.baeldung.bean.injection.Ship;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConstructorBasedShipConfig {

    @Bean
    public Ship ship() {
        return new Ship(helm());
    }

    @Bean
    public Helm helm() {
        return new Helm();
    }
}
