package com.habuma.spitter.alerts;
import com.habuma.spitter.domain.Spittle;

public interface AlertService {
  void sendSpittleAlert(Spittle spittle);
}
