package com.habuma.spitter.client.rmi;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.habuma.spitter.domain.Spitter;
import com.habuma.spitter.domain.Spittle;
import com.habuma.spitter.service.SpitterService;

public class SpitterRmiClient {

  public List<Spittle> getSpittles(String userName) {
    Spitter spitter = spitterService.getSpitter(userName);
    return spitterService.getSpittlesForSpitter(spitter);
  }

  @Autowired
  SpitterService spitterService;

}
