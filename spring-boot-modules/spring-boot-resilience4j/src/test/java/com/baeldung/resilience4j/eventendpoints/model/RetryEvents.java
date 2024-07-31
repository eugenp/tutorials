package com.baeldung.resilience4j.eventendpoints.model;

import java.util.List;

public class RetryEvents {

  private List<RetryEvent> retryEvents;

  public List<RetryEvent> getRetryEvents() {
    return retryEvents;
  }

  public void setRetryEvents(List<RetryEvent> retryEvents) {
    this.retryEvents = retryEvents;
  }
}
