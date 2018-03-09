package com.habuma.spitter.client;

import com.habuma.spitter.domain.Spitter;
import com.habuma.spitter.domain.Spittle;

public interface SpitterClient {
  Spittle[] retrieveSpittlesForSpitter(String username);
  String postSpitter(Spitter spitter);
}
