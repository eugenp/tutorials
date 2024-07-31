package com.baeldung.resilience4j.eventendpoints.model;

import java.time.ZonedDateTime;
import java.util.Objects;

public class RetryEvent {

  private String retryName;
  private String type;
  private ZonedDateTime creationTime;
  private String errorMessage;
  private Integer numberOfAttempts;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    RetryEvent that = (RetryEvent) o;
    return Objects.equals(retryName, that.retryName)
        && Objects.equals(type, that.type)
        && Objects.equals(creationTime, that.creationTime)
        && Objects.equals(errorMessage, that.errorMessage)
        && Objects.equals(numberOfAttempts, that.numberOfAttempts);
  }

  @Override
  public int hashCode() {
    return Objects.hash(retryName, type, creationTime, errorMessage, numberOfAttempts);
  }

  public String getRetryName() {
    return retryName;
  }

  public void setRetryName(String retryName) {
    this.retryName = retryName;
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

  public Integer getNumberOfAttempts() {
    return numberOfAttempts;
  }

  public void setNumberOfAttempts(Integer numberOfAttempts) {
    this.numberOfAttempts = numberOfAttempts;
  }
}
