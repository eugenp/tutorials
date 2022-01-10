package com.baeldung.pattern.cleanarchitecture.hexagonal.business.model;

import java.time.LocalDateTime;

public class Feedback {

  private int id;
  private String content;
  private LocalDateTime createdTs;


  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public LocalDateTime getCreatedTs() {
    return createdTs;
  }

  public void setCreatedTs(LocalDateTime createdTs) {
    this.createdTs = createdTs;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Feedback{");
    sb.append("id=").append(id);
    sb.append(", comment='").append(content).append('\'');
    sb.append(", createdTs=").append(createdTs);
    sb.append('}');
    return sb.toString();
  }
}
