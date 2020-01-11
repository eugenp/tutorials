package com.baeldung.beandefinitionoverrideexception;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan()
public class TestConfiguration1 {

    class TestBean1 {

        private String name;

        public String getName() {
            return name;
        }

    }

    @Bean
    public TestBean1 testBean(){
        return new TestBean1();
    }

}
