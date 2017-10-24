package com.baeldung.constructordi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.constructordi.domain.Bed;
import com.baeldung.constructordi.domain.Room;

@Configuration
public class AnotherConfig {

    @Bean
    public Room room() {
        Room room = new Room(bed());
        return room;
    }

    @Bean
    public Bed bed() {
        return new Bed();
    }

}
