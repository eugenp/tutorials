package com.baeldung.bean.config;

import com.baeldung.bean.injection.Helm;
import com.baeldung.bean.injection.Ship;
import org.springframework.context.annotation.Bean;

public class SetterBasedShipConfig {

    @Bean
    public Ship ship() {
        return new Ship();
    }

    @Bean
    public Helm helm() {
        return new Helm();
    }
}
