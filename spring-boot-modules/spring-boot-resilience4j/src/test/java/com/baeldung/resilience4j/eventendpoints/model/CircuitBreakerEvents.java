package com.baeldung.resilience4j.eventendpoints.model;

import java.util.List;

public class CircuitBreakerEvents {

  private List<CircuitBreakerEvent> circuitBreakerEvents;

  public List<CircuitBreakerEvent> getCircuitBreakerEvents() {
    return circuitBreakerEvents;
  }

  public void setCircuitBreakerEvents(List<CircuitBreakerEvent> circuitBreakerEvents) {
    this.circuitBreakerEvents = circuitBreakerEvents;
  }
}
