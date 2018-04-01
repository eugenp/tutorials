package com.springinaction.springidol;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Provider;

import org.springframework.stereotype.Component;

@Component
public class KnifeJuggler {
  //<start id="provider_constructor"/> 
  private Set<Knife> knives;

  @Inject 
  public KnifeJuggler(Provider<Knife> knifeProvider) {
    knives = new HashSet<Knife>();
    for (int i = 0; i < 5; i++) {
      knives.add(knifeProvider.get());
    }
  }
  //<end id="provider_constructor"/> 
  
  public Set<Knife> getKnives() {
    return knives;
  }
}
