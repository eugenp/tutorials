package com.baeldung.beaninjectiontypes;

import com.baeldung.beaninjectiontypes.vehicle.engine.Engine;
import com.baeldung.beaninjectiontypes.vehicle.engine.EngineReal;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Configuration
    static class ContextConfiguration {

        @Bean
        public Engine engine() {
            return new EngineReal();
        }
    }
}