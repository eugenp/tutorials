package com.baeldung.resilience4j.eventendpoints.model;

import java.time.ZonedDateTime;
import java.util.Objects;

public class RateLimiterEvent {

  private String rateLimiterName;
  private String type;
  private ZonedDateTime creationTime;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    RateLimiterEvent that = (RateLimiterEvent) o;
    return Objects.equals(rateLimiterName, that.rateLimiterName)
        && Objects.equals(type, that.type)
        && Objects.equals(creationTime, that.creationTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(rateLimiterName, type, creationTime);
  }

  public String getRateLimiterName() {
    return rateLimiterName;
  }

  public void setRateLimiterName(String rateLimiterName) {
    this.rateLimiterName = rateLimiterName;
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
}
