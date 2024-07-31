package com.baeldung.resilience4j.eventendpoints.model;

import java.time.ZonedDateTime;
import java.util.Objects;

public class BulkheadEvent {

  private String bulkheadName;
  private String type;
  private ZonedDateTime creationTime;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    BulkheadEvent that = (BulkheadEvent) o;
    return Objects.equals(bulkheadName, that.bulkheadName)
        && Objects.equals(type, that.type)
        && Objects.equals(creationTime, that.creationTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bulkheadName, type, creationTime);
  }

  public String getBulkheadName() {
    return bulkheadName;
  }

  public void setBulkheadName(String bulkheadName) {
    this.bulkheadName = bulkheadName;
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
