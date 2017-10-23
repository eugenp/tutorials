package com.baeldung.constructordi.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Room {

  private Bed bed;
  
  @Autowired
  public Room(Bed bed) {
    this.bed = bed;
  }

}
