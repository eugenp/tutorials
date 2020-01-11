package com.baeldung.beandefinitionoverrideexception;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfiguration2 {

    class TestBean2 {

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    @Bean
    public TestBean2 testBean() {
        return new TestBean2();
    }

}
