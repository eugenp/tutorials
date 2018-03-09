package com.habuma.spitter.email;

import com.habuma.spitter.domain.Spittle;

public interface SpitterEmailService {
  void sendSpittleEmail(String to, Spittle spittle);
}
