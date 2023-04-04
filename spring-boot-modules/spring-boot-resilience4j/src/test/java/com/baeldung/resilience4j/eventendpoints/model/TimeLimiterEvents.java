package com.baeldung.resilience4j.eventendpoints.model;

import java.util.List;

public class TimeLimiterEvents {

  private List<TimeLimiterEvent> timeLimiterEvents;

  public List<TimeLimiterEvent> getTimeLimiterEvents() {
    return timeLimiterEvents;
  }

  public void setTimeLimiterEvents(List<TimeLimiterEvent> timeLimiterEvents) {
    this.timeLimiterEvents = timeLimiterEvents;
  }
}
