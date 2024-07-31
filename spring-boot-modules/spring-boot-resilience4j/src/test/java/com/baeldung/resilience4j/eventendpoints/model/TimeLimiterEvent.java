package com.baeldung.resilience4j.eventendpoints.model;

import java.time.ZonedDateTime;
import java.util.Objects;

public class TimeLimiterEvent {

  private String timeLimiterName;
  private String type;
  private ZonedDateTime creationTime;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TimeLimiterEvent that = (TimeLimiterEvent) o;
    return Objects.equals(timeLimiterName, that.timeLimiterName)
        && Objects.equals(type, that.type)
        && Objects.equals(creationTime, that.creationTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(timeLimiterName, type, creationTime);
  }

  public String getTimeLimiterName() {
    return timeLimiterName;
  }

  public void setTimeLimiterName(String timeLimiterName) {
    this.timeLimiterName = timeLimiterName;
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
