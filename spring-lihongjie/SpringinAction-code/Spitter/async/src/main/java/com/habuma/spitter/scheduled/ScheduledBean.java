package com.habuma.spitter.scheduled;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledBean {

  @Scheduled(cron="0 0 0 * * SAT")
  public void archiveOldSpittles() {
    // ...
  }
  
}
