package com.baeldung.avaje.intro;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class Knight {

  private Sword sword;

  private Shield shield;

  @Inject
  public Knight(Sword sword, Shield shield) {
    this.sword = sword;
    this.shield = shield;
  }

  public Sword sword() {
    return sword;
  }

  public void sword(Sword engine) {
    this.sword = engine;
  }

  public Shield shield() {
    return shield;
  }

  public void shield(Shield brand) {
    this.shield = brand;
  }
}
