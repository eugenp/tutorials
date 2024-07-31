package com.baeldung.avaje.intro;

import io.avaje.inject.BeanScope;
import io.avaje.inject.PostConstruct;
import io.avaje.inject.PreDestroy;
import jakarta.inject.Singleton;

@Singleton
public class Ninja {

  private Sword sword;

  @PostConstruct
  void equip(BeanScope scope) {
    sword = scope.get(Sword.class);
  }

  @PreDestroy
  void dequip() {
    sword = null;
  }

  public Sword sword() {
    return sword;
  }

  public void sword(Sword engine) {
    this.sword = engine;
  }
}
