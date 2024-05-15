package com.baeldung.resilience4j.eventendpoints.model;

import java.time.ZonedDateTime;
import java.util.Objects;

public class CircuitBreakerEvent {

  private String circuitBreakerName;
  private String type;
  private ZonedDateTime creationTime;
  private String errorMessage;
  private Integer durationInMs;
  private String stateTransition;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CircuitBreakerEvent that = (CircuitBreakerEvent) o;
    return Objects.equals(circuitBreakerName, that.circuitBreakerName)
        && Objects.equals(type, that.type)
        && Objects.equals(creationTime, that.creationTime)
        && Objects.equals(errorMessage, that.errorMessage)
        && Objects.equals(durationInMs, that.durationInMs)
        && Objects.equals(stateTransition, that.stateTransition);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        circuitBreakerName, type, creationTime, errorMessage, durationInMs, stateTransition);
  }

  public String getCircuitBreakerName() {
    return circuitBreakerName;
  }

  public void setCircuitBreakerName(String circuitBreakerName) {
    this.circuitBreakerName = circuitBreakerName;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public ZonedDateTime getCreationTime() {
    return creationTime;
  }

  public void setCreationTime(ZonedDateTime creationTime) {
    this.creationTime = creationTime;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public Integer getDurationInMs() {
    return durationInMs;
  }

  public void setDurationInMs(Integer durationInMs) {
    this.durationInMs = durationInMs;
  }

  public String getStateTransition() {
    return stateTransition;
  }

  public void setStateTransition(String stateTransition) {
    this.stateTransition = stateTransition;
  }
}
