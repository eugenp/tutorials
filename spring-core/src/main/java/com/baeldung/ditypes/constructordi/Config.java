package com.baeldung.ditypes.constructordi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.ditypes.constructordi.domain.*;

@Configuration
@ComponentScan("com.baeldung.ditypes.constructordi")
public class Config {

    @Bean
    public Cubicle cubicle() {
        return new Cubicle("John's Cubicle");
    }

    @Bean
    public Livingroom livingRoom() {
        return new Livingroom("John's LivingRoom");
    }

    @Bean
    public Bedroom bedRoom() {
        return new Bedroom("John's BedRoom");
    }

    @Bean
    public Restroom restRoom() {
        return new Restroom("John's RestRoom");
    }

}
