package com.baeldung.setterdi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.setterdi.domain.Bed;
import com.baeldung.setterdi.domain.Room;


@Configuration
@ComponentScan("com.baeldung.setterdi")
public class AnotherConfig {

  @Bean
  public Room room() {
    Room room = new Room();
    room.setBed(bed());
    return room;
  }
  
  @Bean
  public Bed bed() {
    return new Bed();
  }
  
}