package com.baeldung.beaninjection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.baeldung.beaninjection")
public class ApplicationConfiguration {

    @Bean
    public Processor processor() {
        return new Processor("Intel Core i7 4790K");
    }

    @Bean
    public VideoCard videoCard() {
        return new VideoCard("MSI GeForce GTX 1050");
    }

    @Bean
    public Motherboard motherboard() {
        return new Motherboard("ASUS Z170-K");
    }
}
