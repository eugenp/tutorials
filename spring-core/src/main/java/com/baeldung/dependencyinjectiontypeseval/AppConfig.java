package com.baeldung.dependencyinjectiontypeseval;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.baeldung.dependencyinjectiontypeseval")
public class AppConfig {

    @Bean
    public IGraphicsCard getIGraphicsCard() {
        return new NvidiaGraphicsCard();
    }
}
