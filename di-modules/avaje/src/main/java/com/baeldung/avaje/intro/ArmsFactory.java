package com.baeldung.avaje.intro;

import io.avaje.inject.Bean;
import io.avaje.inject.Factory;

@Factory
public class ArmsFactory {

  @Bean
  public Sword provideSword() {
    return new Sword();
  }

  @Bean
  public Shield provideShield() {
    return new Shield(25);
  }
}
