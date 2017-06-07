package org.baeldung.bean.config;

import org.baeldung.bean.injection.Helm;
import org.baeldung.bean.injection.ShipWithFinalDependency;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConstructorBasedFinalDependencyShipConfig {
    
    @Bean
    public ShipWithFinalDependency ShipWithFinalDependency(Helm helm) {
        return new ShipWithFinalDependency(helm);
    }

    @Bean
    public Helm helm() {
        return new Helm();
    }
}
